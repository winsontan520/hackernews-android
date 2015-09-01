package com.winsontan520.hackernews.event;

/**
 * Created by winsontan520 on 8/5/15.
 */
public class OpenUrlEvent {

    String url;

    public OpenUrlEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
