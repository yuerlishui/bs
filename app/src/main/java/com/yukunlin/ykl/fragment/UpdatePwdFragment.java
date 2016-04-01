package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yukunlin.ykl.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePwdFragment extends DialogFragment {
    @ViewInject(R.id.oldPasswordEditText)
    private EditText oldPwd;

    @ViewInject(R.id.newPasswordEditText)
    private EditText newPwd;

    @ViewInject(R.id.confirmPasswordEditText)
    private EditText confirmPwd;

    @ViewInject(R.id.saveButton)
    private Button saveButton;

    public UpdatePwdFragment() {
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
        View root = inflater.inflate(R.layout.fragment_update_pwd, container, false);
        x.view().inject(this, root);
        initEditText();
        return root;
    }

    @Event(value = R.id.saveButton)
    private void saveClick(View view) {

    }

    private void initEditText() {
        TextWatcher textWatcheer = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!oldPwd.getText().toString().isEmpty() && !newPwd.getText().toString().isEmpty() && !confirmPwd.getText().toString().isEmpty()) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        oldPwd.addTextChangedListener(textWatcheer);
        newPwd.addTextChangedListener(textWatcheer);
        confirmPwd.addTextChangedListener(textWatcheer);
    }
}
