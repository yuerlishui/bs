package com.yukunlin.ykl.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by yukunlin on 2016/3/13.
 */
public class User extends BmobUser {
    private String sex;
    private String nick;
    private Integer age;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    @Override
    public String toString() {
        return "User{" +
                "sex='" + sex + '\'' +
                ", nick='" + nick + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
