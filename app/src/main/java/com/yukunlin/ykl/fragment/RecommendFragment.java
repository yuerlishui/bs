package com.yukunlin.ykl.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.yukunlin.ykl.adapter.RecommendAdapter;
import com.yukunlin.ykl.model.Comment;
import com.yukunlin.ykl.model.OneWord;
import com.yukunlin.ykl.model.Question;
import com.yukunlin.ykl.utils.MemoryCache;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends DialogFragment {

    private TextView note;
    private TextView content;
    private NetworkImageView picture;
    private TextView translation;
    private TextView word;
    private TextView nWord;
    private TextView adjWork;
    private TextView nextWord;
    private TextView commentTextView;
    private LinearLayout favour_ly;
    private ImageView favourImageView;
    private TextView favourCount;
    private TextView day;
    private ImageView tts;

    @ViewInject(R.id.ptrLayout)
    PtrClassicFrameLayout ptrLayout;

    @ViewInject(R.id.listView)
    private ListView listView;

    private String url = "http://open.iciba.com/dsapi";
    private RequestQueue mQueue;
    private ImageLoader loader;
    private List<OneWord> wordList;
    private int index = 0;
    private String sid;
    private String ttsUrl;
    private RecommendAdapter adapter;


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
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.recomment_head, listView, false);
        x.view().inject(this, root);
        mQueue = Volley.newRequestQueue(getContext());
        loader = new ImageLoader(mQueue, new MemoryCache());
        initData();
        initHead(headView);
        initPtr();
        nextWordClick();
        return root;
    }

    private void commentClick(final String sid) {
        commentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCommentFragment fragment = new EditCommentFragment();
                fragment.setSid(sid);
                fragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        favour_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favourImageView.setImageResource(R.drawable.icon_like_actived);
                favourCount.setText("1");
            }
        });
        tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    if (ttsUrl != null) {
                        mediaPlayer.setDataSource(ttsUrl);
                    }
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void initHead(View view) {
        note = (TextView) view.findViewById(R.id.note);
        content = (TextView) view.findViewById(R.id.content);
        picture = (NetworkImageView) view.findViewById(R.id.picture);
        translation = (TextView) view.findViewById(R.id.translation);
        word = (TextView) view.findViewById(R.id.word);
        nWord = (TextView) view.findViewById(R.id.nWord);
        adjWork = (TextView) view.findViewById(R.id.adjWord);
        nextWord = (TextView) view.findViewById(R.id.nextWord);
        commentTextView = (TextView) view.findViewById(R.id.commentTextView);
        favour_ly = (LinearLayout) view.findViewById(R.id.favour_ly);
        favourImageView = (ImageView) view.findViewById(R.id.favourImageView);
        favourCount = (TextView) view.findViewById(R.id.favourCount);
        day = (TextView) view.findViewById(R.id.day);
        tts = (ImageView) view.findViewById(R.id.tts);


        listView.addHeaderView(view);
    }

    private void initPtr() {
        ptrLayout.setLastUpdateTimeRelateObject(this);
        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
                queryData();
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
        BmobQuery<OneWord> query = new BmobQuery<>();

        query.findObjects(getContext(), new FindListener<OneWord>() {
            @Override
            public void onSuccess(List<OneWord> list) {
                wordList.addAll(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), "获取数据失败   " + s, Toast.LENGTH_SHORT).show();
            }
        });
        BmobQuery<Comment> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("wordId", sid);
        Log.d("TAG", "queryData: " + sid);
        bmobQuery.findObjects(getContext(), new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                Collections.reverse(list);
//                RecommendAdapter adapter = new RecommendAdapter(list, getContext());
//                listView.setAdapter(adapter);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                commentTextView.setText(list.size() + "");
                ptrLayout.refreshComplete();
            }

            @Override
            public void onError(int i, String s) {
                ptrLayout.refreshComplete();
                Toast.makeText(getActivity(), "获取数据失败   " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void nextWordClick() {
        nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    ttsUrl = jsonObject.getString("tts");
                    sid = jsonObject.getString("sid");
                    String dateline = jsonObject.getString("dateline");
                    String[] split = dateline.split("-");
                    day.setText(split[2]);
                    commentClick(sid);
                    queryData();
                    note.setText(notes);
                    content.setText(contents);
                    translation.setText(translations.substring(5));
                    picture.setImageUrl(picture2, loader);
                    adapter = new RecommendAdapter(new ArrayList<Comment>(),getContext());
                    listView.setAdapter(adapter);
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

//        Question question = new Question();
//        question.save(getContext(), new SaveListener() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}
