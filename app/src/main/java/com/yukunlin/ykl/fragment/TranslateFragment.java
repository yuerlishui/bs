package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.network.MD5_baidu_trans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.android.volley.Request.Method.GET;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslateFragment extends DialogFragment {

    @ViewInject(R.id.resultTextView)
    TextView resultTextView;

    @ViewInject(R.id.content)
    EditText contentEditText;
    private RequestQueue mQueue;

    private String encode;
    private String s;


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
        return root;
    }

    private void loadData() {
        String appid = "20160317000015823";
        String q = "hello";
        String salt = Long.toString(new java.util.Date().getTime());//随机数，官方提供的是获取时间
        String key = "ndM_MLjJQrc2FfNV401n";
        String md5Code = MD5_baidu_trans.GetMD5Code(appid + q + salt + key);
        String trim = contentEditText.getText().toString().trim();

        try {
            encode = URLEncoder.encode(trim, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "q=" + trim + "&from=en&to=zh&appid=" + appid + "&salt=" + salt + "&sign=" + md5Code;
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
