package com.yukunlin.ykl.CumstomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.yukunlin.ykl.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by wonton on 15/11/2.
 */
public class BeautifulOneBtnDialog extends Dialog {


    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.msg)
    TextView msg;
    @InjectView(R.id.line)
    View line;

    @Optional
    @InjectView(R.id.confirm)
    TextView confirm;

    private String titleTxt = "";
    private String msgTxt = "";
    private String confirTxt = "";
    private String cancelTxt = "";
    private ButtonClick click;
    private int msgVisible;
    private boolean isOneBtn = false;

    public void setOneBtn(boolean oneBtn) {
        isOneBtn = oneBtn;
    }

    public void setMsgVisible(int msgVisible) {
        this.msgVisible = msgVisible;
    }

    public void setClick(ButtonClick click) {
        this.click = click;
    }

    public BeautifulOneBtnDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public BeautifulOneBtnDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setTitleTxt(String titleTxt) {
        this.titleTxt = titleTxt;
    }

    public void setMsgTxt(String msgTxt) {
        this.msgTxt = msgTxt;
    }

    public void setConfirTxt(String confirTxt) {
        this.confirTxt = confirTxt;
    }

    public void setCancelTxt(String cancelTxt) {
        this.cancelTxt = cancelTxt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_force_update_app);
        ButterKnife.inject(this);

        title.setText(titleTxt);
        msg.setText(msgTxt);
        confirm.setText(confirTxt);
        msg.setVisibility(msgVisible);
    }

    @OnClick(R.id.confirm)
    void setConfirm() {
        if (click != null) {
            click.onPositive();
        }
    }

    public interface ButtonClick {
        public void onPositive();
    }
}
