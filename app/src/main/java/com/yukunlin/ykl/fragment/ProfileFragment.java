package com.yukunlin.ykl.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.user.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends DialogFragment {
    @ViewInject(R.id.headImageView)
    private ImageView headImageView;

    @ViewInject(R.id.girlImageView)
    private ImageView girlImageView;

    @ViewInject(R.id.boyImageView)
    private ImageView boyImageView;

    @ViewInject(R.id.nameTextView)
    private TextView nameTextView;

    @ViewInject(R.id.phoneEditView)
    private TextView phoneEditView;

    @ViewInject(R.id.rootlay)
    private RelativeLayout rootlay;


    public OnFinishListener onFinishListener;

    public ProfileFragment() {
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
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        x.view().inject(this, root);
        initData();
        return root;
    }

    private void initData() {
        //   User currentUser = BmobUser.getCurrentUser(getContext(), User.class);

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(getContext(), BmobUser.getCurrentUser(getContext()).getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                if (user.getSex().equals("man")) {
                    boyImageView.setSelected(true);
                    girlImageView.setSelected(false);
                    headImageView.setImageResource(R.drawable.default_avatar_male);

                } else {
                    boyImageView.setSelected(false);
                    girlImageView.setSelected(true);
                    headImageView.setImageResource(R.drawable.default_avatar_female);
                }

                nameTextView.setText(user.getName());
                phoneEditView.setText(user.getMobilePhoneNumber());
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Event(value = R.id.submitView)
    private void saveClick(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("正在保存...");
        progressDialog.show();
        User newUser = new User();
        newUser.setMobilePhoneNumber(phoneEditView.getText().toString().trim());
        newUser.setName(nameTextView.getText().toString().trim());
        if (boyImageView.isSelected()) {
            newUser.setSex("man");
        } else {
            newUser.setSex("woman");
        }
        BmobUser bmobUser = BmobUser.getCurrentUser(getContext());
        newUser.update(getContext(), bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onFinishListener.onFinish(nameTextView.getText().toString().trim());
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Event(value = R.id.resetPwdLayout)
    private void resetPwdClick(View view) {
        UpdatePwdFragment fragment = new UpdatePwdFragment();
        fragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
    }

    @Event(value = R.id.backImageButton)
    private void backClick(View view) {
        dismiss();
    }

    @Event(value = R.id.boyImageView)
    private void boyClick(View view) {
       boyImageView.setSelected(true);
        girlImageView.setSelected(false);
    }

    @Event(value = R.id.girlImageView)
    private void girlClick(View view){
        boyImageView.setSelected(false);
        girlImageView.setSelected(true);
    }

    public interface OnFinishListener {
        public void onFinish(String name);
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }
}
