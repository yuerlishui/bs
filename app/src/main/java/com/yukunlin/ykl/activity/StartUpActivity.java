package com.yukunlin.ykl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.yukunlin.ykl.MainActivity;
import com.yukunlin.ykl.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.BmobUser;

public class StartUpActivity extends BaseActivity {
    @ViewInject(R.id.startImageView)
    ImageView startImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        x.view().inject(this);
        startImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BmobUser.getCurrentUser(StartUpActivity.this) != null) {
                    // 允许用户使用应用
                    Intent intent = new Intent(StartUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //缓存用户对象为空时， 可打开用户注册界面…
                    Intent intent = new Intent(StartUpActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);


    }
}
