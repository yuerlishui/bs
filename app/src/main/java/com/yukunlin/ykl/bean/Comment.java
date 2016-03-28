package com.yukunlin.ykl.bean;

/**
 * Created by yukunlin on 16/3/28.
 */
public class Comment {
    private String avatar;
    private String userName;
    private String content;
    private String date;
    private String wordId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "avatar='" + avatar + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", wordId='" + wordId + '\'' +
                '}';
    }
}
