package com.yukunlin.ykl.fragment;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.yukunlin.ykl.CumstomView.BeautifulDialog;
import com.yukunlin.ykl.CumstomView.BeautifulOneBtnDialog;
import com.yukunlin.ykl.MyApplication;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.activity.ReadingActivity;
import com.yukunlin.ykl.activity.ShowReadingActivity;
import com.yukunlin.ykl.activity.ShowTestActivity;
import com.yukunlin.ykl.activity.SingleChooseActivity;
import com.yukunlin.ykl.adapter.TestAdapter;
import com.yukunlin.ykl.model.Question;
import com.yukunlin.ykl.user.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends DialogFragment {

    @ViewInject(R.id.gridView)
    private GridView gridView;

    @ViewInject(R.id.middle)
    private TextView middle;

    @ViewInject(R.id.middleState)
    private TextView middleState;

    @ViewInject(R.id.advanced)
    private TextView advanced;

    @ViewInject(R.id.advancedState)
    private TextView advancedState;

    @ViewInject(R.id.readingMiddle)
    private TextView readingMiddle;

    @ViewInject(R.id.readingMiddleStatue)
    private TextView readingMiddleStatue;

    @ViewInject(R.id.readingAdvanced)
    private TextView readingAdvanced;

    @ViewInject(R.id.readingAdvancedStatue)
    private TextView readingAdvancedStatue;

    private List<String> list;
    private ProgressDialog progressDialog;

    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        x.view().inject(this, root);
        initView();
        return root;
    }

    private void initView() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("请稍后...");
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + 1 + "");
        }
        TestAdapter adapter = new TestAdapter(list, getContext());
        gridView.setAdapter(adapter);
        gridView.setFocusable(false);

        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(getContext(), BmobUser.getCurrentUser(getContext()).getObjectId(), new GetListener<User>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(User user) {
                if (user.getSingleChoice2().equals("unlock")) {
                    //  middle.setBackgroundColor(Color.parseColor("#a8d832"));
                    middle.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_unlock));
                    middleState.setText("已解锁");
                } else {
                    // middle.setBackgroundColor(Color.parseColor("#aaa"));
                    middle.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_text));
                    middleState.setText("未解锁");
                }
                if (user.getSingleChoice3().equals("unlock")) {
                    //  advanced.setBackgroundColor(Color.parseColor("#a8d832"));
                    advanced.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_unlock));
                    advancedState.setText("已解锁");
                } else {
                    // advanced.setBackgroundColor(Color.parseColor("#aaa"));
                    advanced.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_text));
                    advancedState.setText("未解锁");
                }


                if (user.getReading2().equals("unlock")) {
                    //  middle.setBackgroundColor(Color.parseColor("#a8d832"));
                    readingMiddle.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_unlock));
                    readingMiddleStatue.setText("已解锁");
                } else {
                    // middle.setBackgroundColor(Color.parseColor("#aaa"));
                    readingMiddle.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_text));
                    readingMiddleStatue.setText("未解锁");
                }
                if (user.getReading3().equals("unlock")) {
                    //  advanced.setBackgroundColor(Color.parseColor("#a8d832"));
                    readingAdvanced.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_unlock));
                    readingAdvancedStatue.setText("已解锁");
                } else {
                    // advanced.setBackgroundColor(Color.parseColor("#aaa"));
                    readingAdvanced.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_text));
                    readingAdvancedStatue.setText("未解锁");
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent i1 = new Intent(getActivity(), ShowTestActivity.class);
                        i1.putExtra("position", position);
                        startActivity(i1);
                        break;
                    case 1:
                        Intent i2 = new Intent(getActivity(), ShowTestActivity.class);
                        i2.putExtra("position", position);
                        startActivity(i2);
                        break;
                    case 2:
                        Intent i3 = new Intent(getActivity(), ShowReadingActivity.class);
                        i3.putExtra("position", position);
                        startActivity(i3);
                        break;
                    case 3:
                        Intent i4 = new Intent(getActivity(), ShowTestActivity.class);
                        i4.putExtra("position", position);
                        startActivity(i4);
                        break;
                    case 4:
                        Intent i5 = new Intent(getActivity(), ShowTestActivity.class);
                        i5.putExtra("position", position);
                        startActivity(i5);
                        break;
                    case 5:
                        Intent i6 = new Intent(getActivity(), ShowReadingActivity.class);
                        i6.putExtra("position", position);
                        startActivity(i6);
                        break;
                    case 6:
                        Toast.makeText(getContext(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getContext(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(getContext(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(getContext(),"敬请期待",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Event(value = R.id.primary)
    private void buttonClick(View view) {
        Intent intent = new Intent(getActivity(), SingleChooseActivity.class);
        intent.putExtra("level", "primary");
        startActivity(intent);
    }

    @Event(value = R.id.middle)
    private void middleClick(View view) {
        progressDialog.show();
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(getContext(), BmobUser.getCurrentUser(getContext()).getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                progressDialog.dismiss();
                if (user.getSingleChoice2().equals("unlock")) {
                    Intent intent = new Intent(getActivity(), SingleChooseActivity.class);
                    intent.putExtra("level", "middle");
                    startActivity(intent);
                } else {
                    final BeautifulOneBtnDialog beautifulDialog = new BeautifulOneBtnDialog(getContext());
                    beautifulDialog.setTitleTxt("还没有解锁哦");
                    beautifulDialog.setMsgTxt("只有通过下一关才能解锁");
                    beautifulDialog.setOneBtn(true);
                    beautifulDialog.setConfirTxt("确认");
                    beautifulDialog.setMsgVisible(View.VISIBLE);
                    beautifulDialog.setClick(new BeautifulOneBtnDialog.ButtonClick() {
                        @Override
                        public void onPositive() {
                            beautifulDialog.dismiss();
                        }
                    });
                    beautifulDialog.show();

                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    @Event(value = R.id.advanced)
    private void advancedClick(View view) {
        progressDialog.show();
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(getContext(), BmobUser.getCurrentUser(getContext()).getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                progressDialog.dismiss();
                if (user.getSingleChoice3().equals("unlock")) {
                    Intent intent = new Intent(getActivity(), SingleChooseActivity.class);
                    intent.putExtra("level", "advanced");
                    startActivity(intent);
                } else {
                    final BeautifulOneBtnDialog beautifulDialog = new BeautifulOneBtnDialog(getContext());
                    beautifulDialog.setTitleTxt("还没有解锁哦");
                    beautifulDialog.setMsgTxt("只有通过下一关才能解锁");
                    beautifulDialog.setOneBtn(true);
                    beautifulDialog.setConfirTxt("确认");
                    beautifulDialog.setMsgVisible(View.VISIBLE);
                    beautifulDialog.setClick(new BeautifulOneBtnDialog.ButtonClick() {
                        @Override
                        public void onPositive() {
                            beautifulDialog.dismiss();
                        }
                    });
                    beautifulDialog.show();

                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    @Event(value = R.id.readingPrimary)
    private void reading1Click(View view) {
        Intent intent = new Intent(getActivity(), ReadingActivity.class);
        intent.putExtra("level", "primary");
        startActivity(intent);
    }


    @Event(value = R.id.readingMiddle)
    private void readingMiddleClick(View view) {
        progressDialog.show();
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(getContext(), BmobUser.getCurrentUser(getContext()).getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                progressDialog.dismiss();
                if (user.getReading2().equals("unlock")) {
                    Intent intent = new Intent(getActivity(), ReadingActivity.class);
                    intent.putExtra("level", "middle");
                    startActivity(intent);
                } else {
                    final BeautifulOneBtnDialog beautifulDialog = new BeautifulOneBtnDialog(getContext());
                    beautifulDialog.setTitleTxt("还没有解锁哦");
                    beautifulDialog.setMsgTxt("只有通过下一关才能解锁");
                    beautifulDialog.setOneBtn(true);
                    beautifulDialog.setConfirTxt("确认");
                    beautifulDialog.setMsgVisible(View.VISIBLE);
                    beautifulDialog.setClick(new BeautifulOneBtnDialog.ButtonClick() {
                        @Override
                        public void onPositive() {
                            beautifulDialog.dismiss();
                        }
                    });
                    beautifulDialog.show();

                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Event(value = R.id.readingAdvanced)
    private void setReadingAdvancedClick(View view) {
        progressDialog.show();
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(getContext(), BmobUser.getCurrentUser(getContext()).getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                progressDialog.dismiss();
                if (user.getReading3().equals("unlock")) {
                    Intent intent = new Intent(getActivity(), ReadingActivity.class);
                    intent.putExtra("level", "advanced");
                    startActivity(intent);
                } else {
                    final BeautifulOneBtnDialog beautifulDialog = new BeautifulOneBtnDialog(getContext());
                    beautifulDialog.setTitleTxt("还没有解锁哦");
                    beautifulDialog.setMsgTxt("只有通过下一关才能解锁");
                    beautifulDialog.setOneBtn(true);
                    beautifulDialog.setConfirTxt("确认");
                    beautifulDialog.setMsgVisible(View.VISIBLE);
                    beautifulDialog.setClick(new BeautifulOneBtnDialog.ButtonClick() {
                        @Override
                        public void onPositive() {
                            beautifulDialog.dismiss();
                        }
                    });
                    beautifulDialog.show();

                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
}
