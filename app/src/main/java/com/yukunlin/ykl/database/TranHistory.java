package com.yukunlin.ykl.database;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yukunlin on 16/3/23.
 */
@Table(name = "tranHistory")
public class TranHistory {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "note")
    private String note;

    @Column(name = "content")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "TranHistory{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
