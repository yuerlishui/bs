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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.model.Question;
import com.yukunlin.ykl.model.Reading;
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
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ShowReadingActivity extends BaseActivity {

    private int count;
    private int current;
    private boolean wrongMode;
    private RadioButton[] radioButtons = new RadioButton[4];
    private List<Reading> list = new ArrayList<>();


    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    @ViewInject(R.id.question)
    private TextView tv_question;

    @ViewInject(R.id.explaination)
    private TextView tv_explaination;

    @ViewInject(R.id.content)
    private TextView content;

    private DbManager dbManager;
    private String msg = "恭喜你全部回答正确！";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        x.view().inject(this);
        initData();

        initDB();
        initRadioGroup();

       // initIntent();


    }

    private void initDB() {
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbName(BmobUser.getCurrentUser(this).getObjectId() + "reading")
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
        dbManager = x.getDb(config);
    }

    private void initIntent() {
        if (getIntent().getStringExtra("level").equals("primary")) {
            msg = "恭喜你全部回答正确！\n 第二关已经解锁";
        } else if (getIntent().getStringExtra("level").equals("middle")) {
            msg = "恭喜你全部回答正确！\n 第三关已经解锁";
        } else {
            msg = "恭喜你全部回答正确！";
        }
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
//            inputStream = getAssets().open("reading.json");
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
//        count = list.size();
//        current = 0;
//        wrongMode = false;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍后...");
        progressDialog.show();
        BmobQuery<Reading> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(this, new FindListener<Reading>() {
            @Override
            public void onSuccess(List<Reading> listQ) {
                list = listQ;

                count = list.size();
                current = 0;
                wrongMode = false;
                initView();
                progressDialog.dismiss();
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    @Event(value = R.id.btn_next)
    private void nextClick(View view) {
        if (current < count - 1) {
            current++;
            Reading q = list.get(current);
            content.setText(q.content);
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
            new AlertDialog.Builder(ShowReadingActivity.this)
                    .setTitle("提示")
                    .setMessage("已经到达最后一题，是否退出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ShowReadingActivity.this.finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            final List<Integer> wrongList = checkAnswer(list);
            if (wrongList.size() == 0) {
                new AlertDialog.Builder(ShowReadingActivity.this)
                        .setTitle("提示")
                        .setMessage(msg)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User newUser = new User();
                                if (getIntent().getStringExtra("level").equals("primary")) {
                                    newUser.setReading2("unlock");
                                } else if (getIntent().getStringExtra("level").equals("middle")) {
                                    newUser.setReading3("unlock");
                                }
                                BmobUser bmobUser = BmobUser.getCurrentUser(ShowReadingActivity.this);
                                newUser.update(ShowReadingActivity.this, bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        Log.d("TAG", "onFailure: " + msg);
                                    }
                                });
                                ShowReadingActivity.this.finish();
                            }
                        })
                        .show();
            } else {
                new AlertDialog.Builder(ShowReadingActivity.this)
                        .setTitle("提示")
                        .setMessage("您答对了" + (list.size() - wrongList.size()) +
                                "道题目，答错了" + wrongList.size() + "道题目。是否查看错题？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wrongMode = true;
                                List<Reading> newList = new ArrayList<Reading>();
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

                                Reading q = list.get(current);
                                content.setText(q.content);
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
                                ShowReadingActivity.this.finish();
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
            Reading q = list.get(current);
            content.setText(q.content);
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

        Reading q = list.get(0);
        tv_question.setText(q.question);
        content.setText(q.content);
        tv_explaination.setText(q.explaination);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);
    }

    private List<Integer> checkAnswer(List<Reading> list) {
        List<Integer> wrongList = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).answer != list.get(i).selectedAnswer) {
                wrongList.add(i);
            }
        }
        return wrongList;
    }
}
