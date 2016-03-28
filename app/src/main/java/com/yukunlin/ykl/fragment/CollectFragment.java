package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yukunlin.ykl.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectFragment extends DialogFragment implements View.OnClickListener {


    private SingleCollectFragment singleCollectFragment;
    private ReadCollectFragment readCollectFragment;

    @ViewInject(R.id.single)
    private TextView singleTextView;

    @ViewInject(R.id.read)
    private TextView readTextView;


    public CollectFragment() {
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
        View root = inflater.inflate(R.layout.fragment_collect, container, false);
        x.view().inject(this, root);
        initView();

        return root;
    }

    private void initView() {
        singleCollectFragment = new SingleCollectFragment();
        readCollectFragment = new ReadCollectFragment();
        singleTextView.setOnClickListener(this);
        readTextView.setOnClickListener(this);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, singleCollectFragment)
                .show(singleCollectFragment)
                .commit();
    }

    @Event(value = R.id.back)
    private void backClick(View view) {
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.single:
                singleTextView.setSelected(true);
                readTextView.setSelected(false);
                if (!singleCollectFragment.isAdded()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, singleCollectFragment).commit();
                }
                break;
            case R.id.read:
                singleTextView.setSelected(false);
                readTextView.setSelected(true);
                if (!readCollectFragment.isAdded()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction().
                            replace(R.id.fragment_container, readCollectFragment).commit();
                }
                break;
        }
    }
}
