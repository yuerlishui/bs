package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yukunlin.ykl.MyApplication;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.adapter.TranHistoryAdapter;
import com.yukunlin.ykl.database.TranHistory;
import com.yukunlin.ykl.network.MD5_baidu_trans;
import com.yukunlin.ykl.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cn.bmob.v3.BmobUser;

import static com.android.volley.Request.Method.GET;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends DialogFragment {

    @ViewInject(R.id.resultTextView)
    private TextView resultTextView;

    @ViewInject(R.id.content)
    private EditText contentEditText;

    @ViewInject(R.id.spinner)
    private Spinner spinner;

    @ViewInject(R.id.historyListView)
    ListView listView;

    private RequestQueue mQueue;
    private String encode;
    private String s;
    private String origin;
    private String dest;
    private DbManager dbManager;


    public TranslateFragment() {
        // Required empty public constructor
    }

    public static TranslateFragment newInstance() {
        TranslateFragment fragment = new TranslateFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_translate, container, false);
        x.view().inject(this, root);
        mQueue = Volley.newRequestQueue(getContext());
        initSpinner();
        initDB();
        return root;
    }

    private void initDB() {
        Log.d("history", BmobUser.getCurrentUser(getContext()).getObjectId());
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbName(BmobUser.getCurrentUser(getContext()).getObjectId())
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
        dbManager = x.getDb(config);
        try {
            List<TranHistory> histories = dbManager.selector(TranHistory.class).findAll();
            if (histories != null) {
                listView.setAdapter(new TranHistoryAdapter(getContext(), histories));
            }

        } catch (DbException e) {
            e.printStackTrace();
            Log.d("history", e.getMessage());
        }
    }

    private void initSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    origin = "zh";
                    dest = "en";
                } else {
                    origin = "en";
                    dest = "zh";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                origin = "zh";
                dest = "en";
            }
        });
    }

    private void loadData() {
        String trim = contentEditText.getText().toString().trim();
        String appid = "20160317000015823";
        String q = trim;
        String salt = Long.toString(new java.util.Date().getTime());//随机数，官方提供的是获取时间
        String key = "ndM_MLjJQrc2FfNV401n";
        String md5Code = MD5_baidu_trans.GetMD5Code(appid + q + salt + key);


        try {
            encode = URLEncoder.encode(q, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "q=" + encode + "&from=" + origin + "&to=" + dest + "&appid=" + appid + "&salt=" + salt + "&sign=" + md5Code;
        String u = "http://api.fanyi.baidu.com/api/trans/vip/translate" + "?" + url;
        StringRequest req = new StringRequest(
                GET,
                u,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "result:" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray trans_result = jsonObject.getJSONArray("trans_result");
                            for (int i = 0; i < trans_result.length(); i++) {
                                JSONObject object = trans_result.getJSONObject(i);
                                String src = object.getString("src");
                                String dst = object.getString("dst");
                                resultTextView.setText(dst);
                                resultTextView.setVisibility(View.VISIBLE);
                                TranHistory history = new TranHistory();
                                history.setContent(src);
                                history.setNote(dst);
                                try {
                                    dbManager.save(history);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        mQueue.add(req);
    }

    @Event(value = R.id.action)
    private void action(View view) {
        loadData();


    }


}
