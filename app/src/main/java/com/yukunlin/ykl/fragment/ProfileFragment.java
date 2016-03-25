package com.yukunlin.ykl.fragment;


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

import com.yukunlin.ykl.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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

    @ViewInject(R.id.localTextView)
    private TextView localeTextView;

    @ViewInject(R.id.phoneTextView)
    private TextView phoneTextView;

    @ViewInject(R.id.rootlay)
    private RelativeLayout rootlay;

    @ViewInject(R.id.cityTextView)
    private TextView cityTextView;

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
        return root;
    }

    @Event(value = R.id.backImageButton)
    private void backClick(View view) {
        dismiss();
    }

    public interface OnFinishListener {
        public void onFinish();
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }
}
