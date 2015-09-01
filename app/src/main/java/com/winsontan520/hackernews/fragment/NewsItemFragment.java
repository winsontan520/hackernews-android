package com.winsontan520.hackernews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winsontan520.hackernews.HackerNewsApi;
import com.winsontan520.hackernews.R;
import com.winsontan520.hackernews.adapter.CommentListAdapter;
import com.winsontan520.hackernews.data.HackerNewsItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winsontan520 on 8/4/15.
 */
public class NewsItemFragment extends Fragment {
    private static final String TAG = "NewsItemFragment";

    public static final String ARG_ID = "id";
    public static final String ARG_TTILE = "title";
    public static final String ARG_KIDS = "kids";

    int mId;
    @Bind(R.id.title) TextView mTitleView;
    @Bind(R.id.list_view)ExpandableListView expListView;
    CommentListAdapter listAdapter;
    List<HackerNewsItem> listDataHeader;
    HashMap<String, List<HackerNewsItem>> listDataChild;
    List<Integer> mComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        ButterKnife.bind(this, view);

        Bundle b = getArguments();
        mId = b.getInt(ARG_ID);
        String title = b.getString(ARG_TTILE);
        mTitleView.setText(title);
        mComments = b.getIntegerArrayList(ARG_KIDS);

        listDataHeader = new ArrayList<HackerNewsItem>();
        listDataChild = new HashMap<String, List<HackerNewsItem>>();

        listAdapter = new CommentListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //getItem(mId);
        getComments();
    }

    private void getComments() {
        if(mComments == null){
            return;
        }

        for(int id :mComments){
            getComment(id);
        }
    }

    private void getComment(int id) {
        HackerNewsApi.getInstance().getItem(id, new Callback<HackerNewsItem>() {
            @Override
            public void success(HackerNewsItem hackerNewsItem, Response response) {
                onGetCommentSuccess(hackerNewsItem);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Network Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onGetCommentSuccess(HackerNewsItem item) {
        listDataHeader.add(item);
        listAdapter.notifyDataSetChanged();

//        Log.d(TAG, new Gson().toJson(item));
//        Log.d(TAG, "title = " + item.getTitle());
//        Log.d(TAG, "text = " + item.getText());
//        Log.d(TAG, "kids = " + item.getKids());
    }

    private void getReply(int id) {
        HackerNewsApi.getInstance().getItem(id, new Callback<HackerNewsItem>() {
            @Override
            public void success(HackerNewsItem hackerNewsItem, Response response) {
                onGetReplySuccess(hackerNewsItem);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Network Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onGetReplySuccess(HackerNewsItem item) {
        //listDataChild.put(String.valueOf(item.getParent()), item);

//        Log.d(TAG, new Gson().toJson(item));
//        Log.d(TAG, "title = " + item.getTitle());
//        Log.d(TAG, "text = " + item.getText());
//        Log.d(TAG, "kids = " + item.getKids());
    }
}
