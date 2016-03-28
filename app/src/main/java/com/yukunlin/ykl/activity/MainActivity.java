package com.yukunlin.ykl.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.activity.BaseActivity;
import com.yukunlin.ykl.fragment.MyselfFragment;
import com.yukunlin.ykl.fragment.RecommendFragment;
import com.yukunlin.ykl.fragment.TestFragment;
import com.yukunlin.ykl.fragment.TranslateFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.explore)
    private RelativeLayout explore;

    @ViewInject(R.id.shelf)
    private LinearLayout shelf;

    @ViewInject(R.id.news)
    private RelativeLayout news;

    @ViewInject(R.id.person)
    private LinearLayout person;

    private RecommendFragment RecFragment;
    private MyselfFragment myselfFragment;
    private TestFragment testFragment;
    private TranslateFragment tranFragment;
    private long exitTime = 0;
    private int index;
    private Fragment[] fragments;
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        initView();
    }


    private void initView() {
        RecFragment = RecommendFragment.newInstance();
        myselfFragment = MyselfFragment.newInstance();
        testFragment = TestFragment.newInstance();
        tranFragment = TranslateFragment.newInstance();

        fragments = new Fragment[]{RecFragment, testFragment, tranFragment, myselfFragment};

        explore.setOnClickListener(this);
        shelf.setOnClickListener(this);
        news.setOnClickListener(this);
        person.setOnClickListener(this);
        explore.setSelected(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, RecFragment)
                .show(RecFragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (myselfFragment.isVisible() || RecFragment.isVisible() || testFragment.isVisible() || tranFragment.isVisible()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                this.moveTaskToBack(true);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person:
                index = 3;
                shelf.setSelected(false);
                explore.setSelected(false);
                news.setSelected(false);
                person.setSelected(true);
//                if (!myselfFragment.isAdded()) {
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .add(R.id.fragment_container, myselfFragment)
//                            .commit();
//
//
//                }
                break;
            case R.id.shelf:
                index = 1;
                person.setSelected(false);
                explore.setSelected(false);
                news.setSelected(false);
                shelf.setSelected(true);
//                if (!testFragment.isAdded()) {
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fragment_container, testFragment)
//                            .commit();
//                }
                break;
            case R.id.news:
                index = 2;
                person.setSelected(false);
                explore.setSelected(false);
                news.setSelected(true);
                shelf.setSelected(false);
//                if (!tranFragment.isAdded()) {
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fragment_container, tranFragment)
//                            .show(tranFragment)
//                            .commit();
//                }
                break;
            case R.id.explore:
                index = 0;
                shelf.setSelected(false);
                explore.setSelected(true);
                news.setSelected(false);
                person.setSelected(false);
//                if (!RecFragment.isAdded()) {
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fragment_container, RecFragment)
//                            .commit();
//                }
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                ft.add(R.id.fragment_container, fragments[index]);
            }
            ft.show(fragments[index]).commit();
        }
        currentTabIndex = index;

    }
}
