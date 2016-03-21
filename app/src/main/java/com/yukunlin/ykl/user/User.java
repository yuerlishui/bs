package com.yukunlin.ykl.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by yukunlin on 2016/3/13.
 */
public class User extends BmobUser {
    private Boolean sex;
    private String nick;
    private Integer age;

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
