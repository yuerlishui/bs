package com.yukunlin.ykl;

import android.app.Application;

import com.yukunlin.ykl.user.User;

import org.xutils.x;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by yukunlin on 2016/2/17.
 */
public class MyApplication extends Application {
    private static User user;

    public static User getUser() {
        return user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        Bmob.initialize(this, "bf1f51cd7a0b154e34dbe5d4e1aa54c2");
        user = new User();
    }
}
