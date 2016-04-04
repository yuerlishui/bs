package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yukunlin.ykl.R;
import com.yukunlin.ykl.adapter.SingleCollectAdapter;
import com.yukunlin.ykl.module.Question;

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
    private ListView listView;

    private DbManager dbManager;

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
        loadData();
        return root;
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
            List<Question> list = dbManager.selector(Question.class).findAll();
            Log.d("TAG", "loadData: " + list.toString());
            SingleCollectAdapter adapter = new SingleCollectAdapter(getContext(),list);
            listView.setAdapter(adapter);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
