package com.yukunlin.ykl.module;

/**
 * Created by yukunlin on 16/3/23.
 */
public class History {
    private String note;
    private String content;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "History{" +
                "note='" + note + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
