package com.winsontan520.hackernews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.winsontan520.hackernews.DividerItemDecoration;
import com.winsontan520.hackernews.HackerNewsApi;
import com.winsontan520.hackernews.R;
import com.winsontan520.hackernews.adapter.TopStoriesAdapter;
import com.winsontan520.hackernews.data.HackerNewsItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by winsontan520 on 8/4/15.
 */
public class TopStoriesFragment extends Fragment {

    private static final String TAG = "TopStoriesFragment";

    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.itemsRecyclerView) RecyclerView mRecyclerView;
    TopStoriesAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTopStories();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new TopStoriesAdapter(getActivity());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getTopStories();
    }

    private void getTopStories() {
        HackerNewsApi.getInstance().getTopStories(new Callback<List<Integer>>() {
            @Override
            public void success(List<Integer> result, Response response) {
                Log.d(TAG, "success : result size " + result.size());
                onGetTopStoriesSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure : " + error.getLocalizedMessage());
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(getActivity(), "Network Connection Error", Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void onGetTopStoriesSuccess(List<Integer> result) {
        // get 20 first
        for (int i = 0; i < 20; i++) {
            getHackerNewsItem(result.get(i));
        }
    }

    private void getHackerNewsItem(int id) {
        HackerNewsApi.getInstance().getItem(id, new Callback<HackerNewsItem>() {
            @Override
            public void success(HackerNewsItem hackerNewsItem, Response response) {
                onGetHackerNewsItemSuccess(hackerNewsItem);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void onGetHackerNewsItemSuccess(HackerNewsItem item) {
        mAdapter.add(item);
    }
}
