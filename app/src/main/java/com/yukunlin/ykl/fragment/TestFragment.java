package com.yukunlin.ykl.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.activity.SingleChooseActivity;
import com.yukunlin.ykl.adapter.TestAdapter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends DialogFragment {

    @ViewInject(R.id.gridView)
    private GridView gridView;

    private List<String> list;

    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        x.view().inject(this, root);
        initView();
        return root;
    }

    private void initView() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + 1 + "");
        }
        TestAdapter adapter = new TestAdapter(list, getContext());
        gridView.setAdapter(adapter);
        gridView.setFocusable(false);
    }

    @Event(value = R.id.primary)
    private void buttonClick(View view) {
        Intent intent = new Intent(getActivity(), SingleChooseActivity.class);
        startActivity(intent);
    }
}
