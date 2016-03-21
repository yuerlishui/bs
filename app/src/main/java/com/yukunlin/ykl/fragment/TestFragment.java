package com.yukunlin.ykl.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.activity.SingleChooseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends DialogFragment {

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
        return root;
    }

    @Event(value = R.id.button)
    private void buttonClick(View view) {
        Intent intent = new Intent(getActivity(), SingleChooseActivity.class);
        startActivity(intent);
    }
}
