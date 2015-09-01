package com.winsontan520.hackernews.event;

import com.winsontan520.hackernews.data.HackerNewsItem;

/**
 * Created by winsontan520 on 8/4/15.
 */
public class TopStoriesItemEvent {
    HackerNewsItem item;

    public TopStoriesItemEvent(HackerNewsItem item){
        this.item = item;
    }

    public HackerNewsItem getItem() {
        return item;
    }

    public void setItem(HackerNewsItem item) {
        this.item = item;
    }
}
