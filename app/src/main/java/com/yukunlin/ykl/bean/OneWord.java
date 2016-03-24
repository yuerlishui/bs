package com.yukunlin.ykl.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by yukunlin on 16/3/24.
 */
public class OneWord extends BmobObject{
    private String type;
    private String word;
    private String n;
    private String adj;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getAdj() {
        return adj;
    }

    public void setAdj(String adj) {
        this.adj = adj;
    }

    @Override
    public String toString() {
        return "OneWord{" +
                "type='" + type + '\'' +
                ", word='" + word + '\'' +
                ", n='" + n + '\'' +
                ", adj='" + adj + '\'' +
                '}';
    }
}
