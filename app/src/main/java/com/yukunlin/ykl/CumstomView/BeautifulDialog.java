package com.yukunlin.ykl.CumstomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.yukunlin.ykl.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by wonton on 15/11/2.
 */
public class BeautifulDialog extends Dialog {


    private TextView title;
    private TextView msg;
    private View line;
    private ImageView midLine;
    private TextView confirm;
    private TextView cancel;

    private String titleTxt = "";
    private String msgTxt = "";
    private String confirTxt = "";
    private String cancelTxt = "";
    private ButtonClick click;
    private int msgVisible;

    public void setMsgVisible(int msgVisible) {
        this.msgVisible = msgVisible;
    }

    public void setClick(ButtonClick click) {
        this.click = click;
    }

    public BeautifulDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public BeautifulDialog(Context context, int theme) {
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
        this.setContentView(R.layout.dialog_update_app);
        title = (TextView) findViewById(R.id.title);
        msg = (TextView) findViewById(R.id.msg);
        confirm = (TextView) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        line = findViewById(R.id.line);
        midLine = (ImageView) findViewById(R.id.mid_line);

        title.setText(titleTxt);
        msg.setText(msgTxt);
        confirm.setText(confirTxt);
        cancel.setText(cancelTxt);
        msg.setVisibility(msgVisible);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.onPositive();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.onNavitive();
                }
            }
        });
    }


    public interface ButtonClick {
        public void onPositive();

        public void onNavitive();
    }
}
