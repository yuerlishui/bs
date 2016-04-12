package com.yukunlin.ykl.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.model.Comment;
import com.yukunlin.ykl.user.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditCommentFragment extends DialogFragment {
    @ViewInject(R.id.content)
    private EditText content;

    private String sid;

    public EditCommentFragment() {
        // Required empty public constructor
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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
        View root = inflater.inflate(R.layout.fragment_edit_comment, container, false);
        x.view().inject(this, root);
        initView();
        return root;
    }

    private void initView() {

    }

    @Event(value = R.id.done)
    private void doneClick(View view) {

        User currentUser = BmobUser.getCurrentUser(getContext(), User.class);
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("请稍后");
        dialog.show();
        Comment comment = new Comment();
        if (!content.getText().toString().trim().isEmpty()) {
            comment.setContent(content.getText().toString().trim());
            comment.setWordId(sid);
            String time = String.valueOf(System.currentTimeMillis());
            comment.setDate(time);
            comment.setUserName(currentUser.getName());
            comment.save(getContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "发表成功", Toast.LENGTH_LONG).show();
                    dismiss();
                    dialog.dismiss();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(getContext(), "发表失败  " + s, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "内容不能为空", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }

    }

    @Event(value = R.id.cancel)
    private void cancelClick(View view) {
        dismiss();
    }
}
