
package com.winsontan520.hackernews.data;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class HackerNewsItem {

    @Expose
    private Integer id;
    @Expose
    private Boolean deleted;
    @Expose
    private String type;
    @Expose
    private String by;
    @Expose
    private long time;
    @Expose
    private String text;
    @Expose
    private Boolean dead;
    @Expose
    private Integer parent;
    @Expose
    private ArrayList<Integer> kids = new ArrayList<Integer>();
    @Expose
    private String url;
    @Expose
    private Integer score;
    @Expose
    private String title;
    @Expose
    private List<Integer> parts = new ArrayList<Integer>();
    @Expose
    private Integer descendants;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * 
     * @param deleted
     *     The deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The by
     */
    public String getBy() {
        return by;
    }

    /**
     * 
     * @param by
     *     The by
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * 
     * @return
     *     The time
     */
    public long getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The dead
     */
    public Boolean getDead() {
        return dead;
    }

    /**
     * 
     * @param dead
     *     The dead
     */
    public void setDead(Boolean dead) {
        this.dead = dead;
    }

    /**
     * 
     * @return
     *     The parent
     */
    public Integer getParent() {
        return parent;
    }

    /**
     * 
     * @param parent
     *     The parent
     */
    public void setParent(Integer parent) {
        this.parent = parent;
    }

    /**
     * 
     * @return
     *     The kids
     */
    public ArrayList<Integer> getKids() {
        return kids;
    }

    /**
     * 
     * @param kids
     *     The kids
     */
    public void setKids(ArrayList<Integer> kids) {
        this.kids = kids;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 
     * @param score
     *     The score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The parts
     */
    public List<Integer> getParts() {
        return parts;
    }

    /**
     * 
     * @param parts
     *     The parts
     */
    public void setParts(List<Integer> parts) {
        this.parts = parts;
    }

    /**
     * 
     * @return
     *     The descendants
     */
    public Integer getDescendants() {
        return descendants;
    }

    /**
     * 
     * @param descendants
     *     The descendants
     */
    public void setDescendants(Integer descendants) {
        this.descendants = descendants;
    }

}
