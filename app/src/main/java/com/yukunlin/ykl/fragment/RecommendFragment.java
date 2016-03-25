package com.yukunlin.ykl.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yukunlin.ykl.CumstomView.RefreshView;
import com.yukunlin.ykl.R;
import com.yukunlin.ykl.bean.OneWord;
import com.yukunlin.ykl.utils.MemoryCache;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends DialogFragment {
    @ViewInject(R.id.note)
    private TextView note;

    @ViewInject(R.id.content)
    private TextView content;

    @ViewInject(R.id.picture)
    private NetworkImageView picture;

    @ViewInject(R.id.translation)
    private TextView translation;

    @ViewInject(R.id.word)
    private TextView word;

    @ViewInject(R.id.nWord)
    private TextView nWord;

    @ViewInject(R.id.adjWord)
    private TextView adjWork;

    @ViewInject(R.id.ptrLayout)
    PtrClassicFrameLayout ptrLayout;

    private String url = "http://open.iciba.com/dsapi";
    private RequestQueue mQueue;
    private ImageLoader loader;
    private List<OneWord> wordList;
    private int index = 0;

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
        initPtr();
        return root;
    }

    private void initPtr() {
        ptrLayout.setLastUpdateTimeRelateObject(this);
        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        RefreshView refreshView = new RefreshView(getContext());
        ptrLayout.setHeaderView(refreshView);
        ptrLayout.addPtrUIHandler(refreshView);
    }

    private void queryData() {
        wordList = new ArrayList<>();
        BmobQuery<OneWord> query = new BmobQuery<OneWord>();
        query.findObjects(getContext(), new FindListener<OneWord>() {
            @Override
            public void onSuccess(List<OneWord> list) {
                wordList.addAll(list);
                initWord();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), "获取数据失败   " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWord() {

    }

    @Event(value = R.id.nextWord)
    private void nextWordClick(View view) {

        if (wordList != null && wordList.size() != 0) {
            word.setText(wordList.get(index).getWord());
            adjWork.setText(wordList.get(index).getAdj());
            nWord.setText(wordList.get(index).getN());
        }
        index++;
        if (index == 5) {
            index = 0;
        }

    }

    private void initData() {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ptrLayout.refreshComplete();
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
                ptrLayout.refreshComplete();
            }
        });
        mQueue.add(request);

    }

}
