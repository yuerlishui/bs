package com.yukunlin.ykl.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yukunlin.ykl.MainActivity;
import com.yukunlin.ykl.MyApplication;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.user.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.accountEditText)
    EditText accountEditText;

    @ViewInject(R.id.passwordEditText)
    EditText passwordEditText;

    @ViewInject(R.id.login)
    Button login;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        initBtn();
        doLogin();
    }

    @Event(value = R.id.backImageView)
    private void onBack(View view) {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }


    private void initBtn() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (accountEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                    login.setEnabled(false);
                } else {
                    login.setEnabled(true);
                }
            }
        };
        accountEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
    }

    private void doLogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("请稍后...");
                dialog.show();

                if (MyApplication.getUser() == null) {
                    user = new User();
                } else {
                    user = MyApplication.getUser();
                }
                user.setUsername(accountEditText.getText().toString().trim());
                user.setPassword(passwordEditText.getText().toString().trim());
                user.login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
