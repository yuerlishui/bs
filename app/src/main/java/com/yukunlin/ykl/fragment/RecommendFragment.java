package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.data.MainPage;
import com.yukunlin.ykl.utils.MemoryCache;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends DialogFragment {
    @ViewInject(R.id.note)
    TextView note;

    @ViewInject(R.id.content)
    TextView content;

    @ViewInject(R.id.picture)
    NetworkImageView picture;

    @ViewInject(R.id.translation)
    TextView translation;

    private String url = "http://open.iciba.com/dsapi";
    private RequestQueue mQueue;
    private ImageLoader loader;

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recommend, container, false);
        x.view().inject(this, root);
        mQueue = Volley.newRequestQueue(getContext());
        loader = new ImageLoader(mQueue, new MemoryCache());
        initData();
        queryData();
        return root;
    }

    private void queryData() {
        BmobQuery<MainPage> query = new BmobQuery<MainPage>();
        query.getObject(getActivity(), "882aa41ca4", new GetListener<MainPage>() {
            @Override
            public void onSuccess(MainPage mainPage) {
                Log.d("MainPage", mainPage.toString());
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), "获取数据失败   " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String notes = jsonObject.getString("note");
                    String contents = jsonObject.getString("content");
                    String picture2 = jsonObject.getString("picture2");
                    String translations = jsonObject.getString("translation");
                    String tts = jsonObject.getString("tts");
                    note.setText(notes);
                    content.setText(contents);
                    translation.setText(translations);
                    picture.setImageUrl(picture2, loader);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }

}
