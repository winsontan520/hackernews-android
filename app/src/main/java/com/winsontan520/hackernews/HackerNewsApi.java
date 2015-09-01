package com.winsontan520.hackernews;

import com.winsontan520.hackernews.data.HackerNewsItem;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by winsontan520 on 7/31/15.
 */
public class HackerNewsApi {
    private static final HackerNewsApi INSTANCE = new HackerNewsApi();
    private final RestAdapter mRestAdapter = new RestAdapter.Builder().setEndpoint(HackerNewsApiConstant.API_URL_BASE).build();
    private final IHackerNewsApi mApi = mRestAdapter.create(IHackerNewsApi.class);

    public static final HackerNewsApi getInstance() {
        return INSTANCE;
    }

    public void getTopStories(Callback<List<Integer>> cb) {
        if (cb != null) {
            mApi.getTopStories(cb);
        }
    }

    public void getItem(int id, Callback<HackerNewsItem> cb){
        if(cb != null){
            mApi.getItem(id, cb);
        }
    }
}
