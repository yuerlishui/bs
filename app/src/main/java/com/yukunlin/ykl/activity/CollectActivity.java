package com.yukunlin.ykl.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.fragment.ReadCollectFragment;
import com.yukunlin.ykl.fragment.SingleCollectFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class CollectActivity extends BaseActivity implements View.OnClickListener {
    private SingleCollectFragment singleCollectFragment;
    private ReadCollectFragment readCollectFragment;

    @ViewInject(R.id.single)
    private TextView singleTextView;

    @ViewInject(R.id.read)
    private TextView readTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        singleCollectFragment = new SingleCollectFragment();
        readCollectFragment = new ReadCollectFragment();
        singleTextView.setOnClickListener(this);
        readTextView.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, singleCollectFragment)
                .show(singleCollectFragment)
                .commit();
    }

    @Event(value = R.id.back)
    private void backClick(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.single:
                singleTextView.setSelected(true);
                readTextView.setSelected(false);
                if (!singleCollectFragment.isAdded()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, singleCollectFragment).commit();
                }
                break;
            case R.id.read:
                singleTextView.setSelected(false);
                readTextView.setSelected(true);
                if (!readCollectFragment.isAdded()) {
                    getSupportFragmentManager()
                            .beginTransaction().
                            replace(R.id.fragment_container, readCollectFragment).commit();
                }
                break;
        }
    }
}
