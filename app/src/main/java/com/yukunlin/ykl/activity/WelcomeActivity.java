package com.yukunlin.ykl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yukunlin.ykl.R;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        x.view().inject(this);
    }

    @Event(value = R.id.login)
    private void doLogin(View view) {
        startActivity(new Intent(this,LoginActivity.class));


    }

    @Event(value = R.id.register)
    private void doRegister(View view) {
        startActivity(new Intent(this,RegisterActivity.class));

    }
}
