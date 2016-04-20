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
    private String singleChoice1;
    private String singleChoice2;
    private String singleChoice3 ;
    private String reading1 ;
    private String reading2;
    private String reading3;

    public String getSingleChoice1() {
        return singleChoice1;
    }

    public void setSingleChoice1(String singleChoice1) {
        this.singleChoice1 = singleChoice1;
    }

    public String getSingleChoice2() {
        return singleChoice2;
    }

    public void setSingleChoice2(String singleChoice2) {
        this.singleChoice2 = singleChoice2;
    }

    public String getSingleChoice3() {
        return singleChoice3;
    }

    public void setSingleChoice3(String singleChoice3) {
        this.singleChoice3 = singleChoice3;
    }

    public String getReading1() {
        return reading1;
    }

    public void setReading1(String reading1) {
        this.reading1 = reading1;
    }

    public String getReading2() {
        return reading2;
    }

    public void setReading2(String reading2) {
        this.reading2 = reading2;
    }


    public String getReading3() {

        return reading3;
    }

    public void setReading3(String reading3) {
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
                ", singleChoice1='" + singleChoice1 + '\'' +
                ", singleChoice2='" + singleChoice2 + '\'' +
                ", singleChoice3='" + singleChoice3 + '\'' +
                ", reading1='" + reading1 + '\'' +
                ", reading2='" + reading2 + '\'' +
                ", reading3='" + reading3 + '\'' +
                '}';
    }
}
