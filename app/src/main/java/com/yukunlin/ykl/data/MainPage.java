package com.yukunlin.ykl.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by yukunlin on 2016/3/14.
 */
public class MainPage extends BmobObject {
    private String content;
    private String note;
    private String pic;
    private String translation;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "MainPage{" +
                "content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", pic='" + pic + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}
