package com.winsontan520.hackernews.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.winsontan520.hackernews.R;
import com.winsontan520.hackernews.data.HackerNewsItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by winsontan520 on 8/11/15.
 */
public class CommentListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<HackerNewsItem> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<HackerNewsItem>> _listDataChild;

    public CommentListAdapter(Context context, List<HackerNewsItem> listDataHeader,
                              HashMap<String, List<HackerNewsItem>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        HackerNewsItem item = this._listDataHeader.get(groupPosition);
        return this._listDataChild.get(String.valueOf(item.getId()))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        HackerNewsItem item = (HackerNewsItem) getChild(groupPosition, childPosition);
        String childText = item.getText();

        //final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.reply, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.reply);

        Spanned spanned = Html.fromHtml(childText);
        txtListChild.setText(spanned);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        HackerNewsItem item = this._listDataHeader.get(groupPosition);
        return this._listDataChild.get(String.valueOf(item.getId()))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        HackerNewsItem commentItem = (HackerNewsItem) getGroup(groupPosition);
        String headerTitle = commentItem.getText();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.comment, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.comment);
        lblListHeader.setTypeface(null, Typeface.NORMAL);

        Spanned spanned = Html.fromHtml(headerTitle);
        lblListHeader.setText(spanned);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
