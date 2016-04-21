package com.yukunlin.ykl.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import cn.bmob.v3.BmobObject;

/**
 * Created by ��Ⱥ on 2015/5/27.
 */
@Table(name = "question")
public class Reading extends BmobObject{
    @Column(name = "question")
    public String question;

    @Column(name = "answerA")
    public String answerA;

    @Column(name = "answerB")
    public String answerB;

    @Column(name = "answerC")
    public String answerC;

    @Column(name = "answerD")
    public String answerD;

    @Column(name = "answer")
    public int answer;

    @Column(name = "explaination")
    public String explaination;

    @Column(name = "id", isId = true)
    public int id;

    @Column(name = "selectedAnswer")
    public int selectedAnswer;

    @Column(name = "content")
    public String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", answer=" + answer +
                ", explaination='" + explaination + '\'' +
                ", id=" + id +
                ", selectedAnswer=" + selectedAnswer +
                ", content='" + content + '\'' +
                '}';
    }
}
