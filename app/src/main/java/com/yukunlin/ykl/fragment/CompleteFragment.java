package com.yukunlin.ykl.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yukunlin.ykl.MainActivity;
import com.yukunlin.ykl.MyApplication;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.user.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteFragment extends DialogFragment {
    @ViewInject(R.id.user_name)
    private EditText usernameEditText;

    @ViewInject(R.id.done_btn)
    private Button doneBtn;

    @ViewInject(R.id.boyImageView)
    private ImageView boyImageView;

    @ViewInject(R.id.girlImageView)
    private ImageView girlImageView;

    @ViewInject(R.id.avatar)
    private RoundedImageView avatarImageView;

    public CompleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_complete, container, false);
        x.view().inject(this, root);
        initUserNameEditText();
        initSexLayout();
        return root;
    }

    private void initUserNameEditText() {
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    doneBtn.setEnabled(true);
                    doneBtn.setTextColor(getActivity().getResources().getColor(R.color.tomato_color));
                } else {
                    doneBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initSexLayout() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.boyImageView:
                        boyImageView.setSelected(true);
                        girlImageView.setSelected(false);
//                        if (path == null) {
                        avatarImageView.setImageResource(R.drawable.default_avatar_male);
//                        }
                        break;
                    case R.id.girlImageView:
                        boyImageView.setSelected(false);
                        girlImageView.setSelected(true);
//                        if (path == null) {
                        avatarImageView.setImageResource(R.drawable.default_avatar_female);
//                        }
                        break;
                }
            }
        };
        boyImageView.setOnClickListener(onClickListener);
        girlImageView.setOnClickListener(onClickListener);
        boyImageView.setSelected(true);
    }

    @Event(value = R.id.done_btn)
    private void doneClick(View view) {
        if (checkInfoOK()) {
            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage("请稍后...");
            dialog.show();
            final User user = MyApplication.getUser();
            if (boyImageView.isSelected()) {
                user.setSex("man");
            } else {
                user.setSex("woman");
            }
            user.setName(usernameEditText.getText().toString().trim());
            user.save(getContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                    doLogin(user);
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }
    }
    private void doLogin(User user) {
        user.login(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                dismiss();
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
               dismiss();
            }
        });
    }
    private boolean checkInfoOK() {
        if (usernameEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), R.string.usernameCannotBeNull, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
