package com.winsontan520.hackernews;

import com.winsontan520.hackernews.data.HackerNewsItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by winsontan520 on 7/31/15.
 */
public interface IHackerNewsApi {

    @GET("/topstories.json")
    void getTopStories(Callback<List<Integer>> cb);

    @GET("/item/{id}.json")
    void getItem(@Path("id") int id, Callback<HackerNewsItem> cb);

}
