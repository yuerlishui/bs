package com.yukunlin.ykl.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.model.Question;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSingleFragment extends DialogFragment {


    private Question data;

    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    @ViewInject(R.id.question)
    private TextView tv_question;

    @ViewInject(R.id.explaination)
    private TextView tv_explaination;

    private RadioButton[] radioButtons = new RadioButton[4];

    public ShowSingleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_show_single, container, false);
        radioButtons[0] = (RadioButton) root.findViewById(R.id.answerA);
        radioButtons[1] = (RadioButton) root.findViewById(R.id.answerB);
        radioButtons[2] = (RadioButton) root.findViewById(R.id.answerC);
        radioButtons[3] = (RadioButton) root.findViewById(R.id.answerD);
        x.view().inject(this, root);
        initView();
        return root;
    }

    private void initView() {

        tv_question.setText(data.getQuestion());
        tv_explaination.setText(data.getExplaination());
        radioButtons[0].setText(data.getAnswerA());
        radioButtons[1].setText(data.getAnswerB());
        radioButtons[2].setText(data.getAnswerC());
        radioButtons[3].setText(data.getAnswerD());

        radioGroup.clearCheck();
        if (data.selectedAnswer != -1) {
            radioButtons[data.selectedAnswer].setChecked(true);
            radioButtons[data.selectedAnswer].setTextColor(Color.RED);
        }

    }

    public void setData(Question data) {
        this.data = data;
    }
}
