package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.data.MainPage;

import org.xutils.x;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends DialogFragment {

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recommend, container, false);
        x.view().inject(this, root);
          initData();
        queryData();
        return root;
    }

    private void queryData() {
        BmobQuery<MainPage> query = new BmobQuery<MainPage>();
        query.getObject(getActivity(), "882aa41ca4", new GetListener<MainPage>() {
            @Override
            public void onSuccess(MainPage mainPage) {
                Log.d("MainPage", mainPage.toString());
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), "获取数据失败   " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
//        MainPage mainPage = new MainPage();
//        mainPage.setContent("比目");
//        mainPage.setNote("89");
//        mainPage.setPic("dfjhdkgjsa");
//        mainPage.save(getActivity(), new SaveListener() {
//
//            @Override
//            public void onSuccess() {
//                Toast.makeText(getActivity(), "数据添加成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int code, String arg0) {
//                // 添加失败
//                Toast.makeText(getActivity(), "数据添加失败  " + arg0, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
