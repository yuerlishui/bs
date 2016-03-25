package com.yukunlin.ykl.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yukunlin.ykl.CumstomView.BeautifulDialog;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.activity.WelcomeActivity;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyselfFragment extends DialogFragment {

    public MyselfFragment() {
        // Required empty public constructor
    }

    public static MyselfFragment newInstance() {
        MyselfFragment fragment = new MyselfFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_myself, container, false);
        x.view().inject(this, root);
        return root;
    }

    @Event(value = R.id.exit)
    private void onExitClick(View view) {
        final BeautifulDialog dialog = new BeautifulDialog(getActivity());
        dialog.setTitleTxt("确认退出吗?");
        dialog.setCancelTxt("取消");
        dialog.setConfirTxt("确认");
        dialog.show();
        dialog.setClick(new BeautifulDialog.ButtonClick() {
            @Override
            public void onPositive() {
                BmobUser.logOut(getContext());   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(getContext()); // 现在的currentUser是null了
                getActivity().finish();
                startActivity(new Intent(getActivity(), WelcomeActivity.class));
            }

            @Override
            public void onNavitive() {
                dialog.dismiss();
            }
        });

    }
    @Event(value = R.id.setting)
    private void settingClick(View view){
        ProfileFragment modifyProfileFragment = new ProfileFragment();
        modifyProfileFragment.setOnFinishListener(new ProfileFragment.OnFinishListener() {
            @Override
            public void onFinish() {
               // updatePerson();
            }
        });
        FragmentManager fm = getActivity().getSupportFragmentManager();
        modifyProfileFragment.show(fm, "dialogFragment");
    }
}
