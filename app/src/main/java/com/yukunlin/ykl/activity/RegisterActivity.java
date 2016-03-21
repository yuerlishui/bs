package com.yukunlin.ykl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yukunlin.ykl.MyApplication;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.user.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
    @ViewInject(R.id.accountEditText)
    EditText accountEditText;

    @ViewInject(R.id.passwordEditText)
    EditText passwordEditText;

    @ViewInject(R.id.register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        x.view().inject(this);
        initBtn();
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
                    register.setEnabled(false);
                } else {
                    register.setEnabled(true);
                }
            }
        };
        accountEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
    }
    @Event(value = R.id.register)
    private void onRegister(View view) {
        User user = MyApplication.getUser();
        user.setUsername(accountEditText.getText().toString().trim());
        user.setPassword(passwordEditText.getText().toString().trim());
        user.signUp(RegisterActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Event(value = R.id.backImageView)
    private void onBack(View view) {
        startActivity(new Intent(this, WelcomeActivity.class));

    }


}
