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
    private boolean singleChoice1;
    private boolean singleChoice2;
    private boolean singleChoice3 ;
    private boolean reading1 ;
    private boolean reading2;
    private boolean reading3;


    public boolean isSingleChoice1() {
        return singleChoice1;
    }

    public void setSingleChoice1(boolean singleChoice1) {
        this.singleChoice1 = singleChoice1;
    }

    public boolean isSingleChoice2() {
        return singleChoice2;
    }

    public void setSingleChoice2(boolean singleChoice2) {
        this.singleChoice2 = singleChoice2;
    }

    public boolean isSingleChoice3() {
        return singleChoice3;
    }

    public void setSingleChoice3(boolean singleChoice3) {
        this.singleChoice3 = singleChoice3;
    }

    public boolean isReading1() {
        return reading1;
    }

    public void setReading1(boolean reading1) {
        this.reading1 = reading1;
    }

    public boolean isReading2() {
        return reading2;
    }

    public void setReading2(boolean reading2) {
        this.reading2 = reading2;
    }
 
    public boolean isReading3() {
        return reading3;
    }

    public void setReading3(boolean reading3) {
        this.reading3 = reading3;
    }

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
                ", singleChoice1=" + singleChoice1 +
                ", singleChoice2=" + singleChoice2 +
                ", singleChoice3=" + singleChoice3 +
                ", reading1=" + reading1 +
                ", reading2=" + reading2 +
                ", reading3=" + reading3 +
                '}';
    }
}
