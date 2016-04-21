package com.yukunlin.ykl.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.model.Question;
import com.yukunlin.ykl.user.User;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ShowTestActivity extends BaseActivity {
    private int count;
    private int current;
    private boolean wrongMode;
    private RadioButton[] radioButtons = new RadioButton[4];
    private List<Question> list = new ArrayList<>();
    private String msg = "恭喜你全部回答正确！";


    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    @ViewInject(R.id.question)
    private TextView tv_question;

    @ViewInject(R.id.explaination)
    private TextView tv_explaination;
    private DbManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_test);
        x.view().inject(this);
        initData();

        initRadioGroup();
        initDB();
        //initIntent();
    }

    private void initIntent() {
        if (getIntent().getStringExtra("level").equals("primary")) {
            msg = "恭喜你全部回答正确！\n 第二关已经解锁";
        } else if (getIntent().getStringExtra("level").equals("middle")) {
            msg = "恭喜你全部回答正确！\n 第三关已经解锁";
        }else {
            msg = "恭喜你全部回答正确！";
        }
    }

    private void initDB() {
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbName(BmobUser.getCurrentUser(this).getObjectId() + "singlecollect")
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
        dbManager = x.getDb(config);
    }

    private void initRadioGroup() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < 4; i++) {
                    if (radioButtons[i].isChecked() == true) {
                        list.get(current).selectedAnswer = i;
                        break;
                    }
                }
            }
        });
    }

    private void initData() {
//        InputStream inputStream = null;
//        try {
//            inputStream = getAssets().open("question.json");
//            byte[] bytes = new byte[inputStream.available()];
//            inputStream.read(bytes);
//            String unitJson = new String(bytes, Charset.forName("utf-8"));
//            Gson gson = new Gson();
//            list = gson.fromJson(unitJson, new TypeToken<ArrayList<Question>>() {
//            }.getType());
//        } catch (IOException e) {
//            list = new ArrayList<Question>();
//            e.printStackTrace();
//        }
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("正在下载...");
        dialog.setMessage("请稍后...");
        dialog.show();
        BmobQuery<Question> query = new BmobQuery<>();
        query.findObjects(this, new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> listQ) {
                Collections.reverse(listQ);
                list = listQ;
                Log.d("TAG", "onSuccess: "+list.size());
                initView();
                count = list.size();
                current = 0;
                wrongMode = false;
                dialog.dismiss();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ShowTestActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Event(value = R.id.btn_next)
    private void nextClick(View view) {
        if (current < count - 1) {
            current++;
            Question q = list.get(current);
            tv_question.setText(q.question);
            radioButtons[0].setText(q.answerA);
            radioButtons[1].setText(q.answerB);
            radioButtons[2].setText(q.answerC);
            radioButtons[3].setText(q.answerD);
            tv_explaination.setText(q.explaination);

            radioGroup.clearCheck();
            if (q.selectedAnswer != -1) {
                radioButtons[q.selectedAnswer].setChecked(true);
            }
        } else if (current == count - 1 && wrongMode == true) {
            new AlertDialog.Builder(ShowTestActivity.this)
                    .setTitle("提示")
                    .setMessage("已经到达最后一题，是否退出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShowTestActivity.this.finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            final List<Integer> wrongList = checkAnswer(list);
            if (wrongList.size() == 0) {
                new AlertDialog.Builder(ShowTestActivity.this)
                        .setTitle("提示")
                        .setMessage(msg)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User newUser = new User();
                                if (getIntent().getStringExtra("level").equals("primary")) {
                                    newUser.setSingleChoice2("unlock");
                                } else if (getIntent().getStringExtra("level").equals("middle")) {
                                    newUser.setSingleChoice3("unlock");
                                }
                                BmobUser bmobUser = BmobUser.getCurrentUser(ShowTestActivity.this);
                                newUser.update(ShowTestActivity.this, bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        Log.d("TAG", "onFailure: " + msg);
                                    }
                                });

                                ShowTestActivity.this.finish();
                            }
                        })
                        .show();
            } else {
                new AlertDialog.Builder(ShowTestActivity.this)
                        .setTitle("提示")
                        .setMessage("您答对了" + (list.size() - wrongList.size()) +
                                "道题目，答错了" + wrongList.size() + "道题目。是否查看错题？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wrongMode = true;
                                List<Question> newList = new ArrayList<Question>();
                                for (int i = 0; i < wrongList.size(); i++) {
                                    newList.add(list.get(wrongList.get(i)));
                                }
                                try {
                                    dbManager.save(newList);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }

                                list.clear();
                                for (int i = 0; i < newList.size(); i++) {
                                    list.add(newList.get(i));
                                }

                                current = 0;
                                count = list.size();

                                Question q = list.get(current);
                                tv_question.setText(q.question);
                                radioButtons[0].setText(q.answerA);
                                radioButtons[1].setText(q.answerB);
                                radioButtons[2].setText(q.answerC);
                                radioButtons[3].setText(q.answerD);
                                tv_explaination.setText(q.explaination);
                                tv_explaination.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ShowTestActivity.this.finish();
                            }
                        })
                        .show();
            }

        }
        initRadioGroup();
    }

    @Event(value = R.id.btn_previous)
    private void preClick(View view) {
        if (current > 0) {
            current--;
            Question q = list.get(current);
            tv_question.setText(q.question);
            radioButtons[0].setText(q.answerA);
            radioButtons[1].setText(q.answerB);
            radioButtons[2].setText(q.answerC);
            radioButtons[3].setText(q.answerD);
            tv_explaination.setText(q.explaination);

            radioGroup.clearCheck();
            if (q.selectedAnswer != -1) {
                radioButtons[q.selectedAnswer].setChecked(true);
            }
        }
        initRadioGroup();
    }


    private void initView() {

        radioButtons[0] = (RadioButton) findViewById(R.id.answerA);
        radioButtons[1] = (RadioButton) findViewById(R.id.answerB);
        radioButtons[2] = (RadioButton) findViewById(R.id.answerC);
        radioButtons[3] = (RadioButton) findViewById(R.id.answerD);

        Question q = list.get(0);
        tv_question.setText(q.question);
        tv_explaination.setText(q.explaination);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);
    }

    private List<Integer> checkAnswer(List<Question> list) {
        List<Integer> wrongList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }
}
