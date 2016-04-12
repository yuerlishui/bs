package com.yukunlin.ykl.fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ListView;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.adapter.SingleCollectAdapter;
import com.yukunlin.ykl.model.Question;
import com.yukunlin.ykl.swipemenulistview.SwipeMenu;
import com.yukunlin.ykl.swipemenulistview.SwipeMenuCreator;
import com.yukunlin.ykl.swipemenulistview.SwipeMenuItem;
import com.yukunlin.ykl.swipemenulistview.SwipeMenuListView;
import com.yukunlin.ykl.utils.SimpleUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleCollectFragment extends DialogFragment {

    @ViewInject(R.id.listView)
    private SwipeMenuListView listView;

    private DbManager dbManager;
    private List<Question> list;
    private SingleCollectAdapter adapter;

    public SingleCollectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_single_collect, container, false);
        x.view().inject(this, root);
        initDB();
        initView();
        loadData();
        return root;
    }

    private void initView() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
//                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(SimpleUtils.dip2px(getContext(), 90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(SimpleUtils.dip2px(getContext(), 90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);
        // other setting
        listView.setCloseInterpolator(new BounceInterpolator());

    }

    private void initDB() {
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbName(BmobUser.getCurrentUser(
                        getContext()).getObjectId() + "singlecollect")
                .setDbVersion(1)
                .setDbUpgradeListener(
                        new DbManager.DbUpgradeListener() {
                            @Override
                            public void onUpgrade(
                                    DbManager db, int oldVersion, int newVersion) {

                            }
                        });
        dbManager = x.getDb(config);
    }

    private void loadData() {
        try {
            list = dbManager.selector(Question.class).findAll();
            Log.d("TAG", "loadData: " + list.toString());
            adapter = new SingleCollectAdapter(getContext(), list);
            listView.setAdapter(adapter);
            // step 2. listener item click event
            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                    Question question = list.get(position);
                    switch (index) {
                        case 0:
                            // open
                            //   open(item);
                            try {
                                dbManager.delete(list.get(position));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            break;
                        case 1:
                            // delete
//					delete(item);

                            break;
                    }
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void delete(ApplicationInfo item) {
        // delete app
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", item.packageName, null));
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void open(ApplicationInfo item) {
        // open app
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(item.packageName);
        List<ResolveInfo> resolveInfoList = getActivity().getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);

            intent.setComponent(componentName);
            startActivity(intent);
        }

    }
}