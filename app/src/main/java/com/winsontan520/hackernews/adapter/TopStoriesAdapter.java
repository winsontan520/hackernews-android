package com.winsontan520.hackernews.adapter;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.winsontan520.hackernews.MainActivity;
import com.winsontan520.hackernews.R;
import com.winsontan520.hackernews.data.HackerNewsItem;
import com.winsontan520.hackernews.event.OpenUrlEvent;
import com.winsontan520.hackernews.event.TopStoriesItemEvent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by winsontan520 on 7/31/15.
 */
public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.ViewHolder> {
    private static final String TAG = "TopStoriesAdapter";
    private SortedList<HackerNewsItem> mDataset;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.title) TextView title;
        @Bind(R.id.author) TextView author;
        @Bind(R.id.time) TextView time;
        @Bind(R.id.point) TextView point;
        @Bind(R.id.url) Button urlButton;
        @Bind(R.id.comment) TextView comment;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            HackerNewsItem item = mDataset.get(position);
            MainActivity.BUS.post(new TopStoriesItemEvent(item));
        }

    }


    public TopStoriesAdapter(Context context) {
        mContext = context;

        mDataset = new SortedList<>(HackerNewsItem.class, new SortedList.Callback<HackerNewsItem>() {
            @Override
            public int compare(HackerNewsItem o1, HackerNewsItem o2) {
                return o1.getId().compareTo(o2.getId());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(HackerNewsItem oldItem, HackerNewsItem newItem) {
                if (oldItem == null || newItem == null) {
                    return false;
                }

                boolean areContentsTheSame = false;
                if (oldItem.getTitle() == null) {
                    if (newItem.getTitle() == null) {
                        areContentsTheSame = true;
                    } else {
                        areContentsTheSame = false;
                    }
                } else {
                    if (oldItem.getTitle().equals(newItem.getTitle())) {
                        areContentsTheSame = true;
                    }
                }

                Log.d(TAG, "oldItem = " + oldItem.getTitle());
                Log.d(TAG, "newItem = " + newItem.getTitle());
                Log.d(TAG, "areContentsTheSame = " + areContentsTheSame);
                return areContentsTheSame;
            }

            @Override
            public boolean areItemsTheSame(HackerNewsItem item1, HackerNewsItem item2) {
                if (item1 == null || item2 == null) {
                    return false;
                }
                boolean areItemsTheSame = item1.getId().equals(item2.getId());
                Log.d(TAG, "item1 = " + item1.getId());
                Log.d(TAG, "item2 = " + item2.getId());
                Log.d(TAG, "areItemsTheSame = " + areItemsTheSame);
                return areItemsTheSame;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HackerNewsItem item = mDataset.get(position);
        holder.title.setText(String.valueOf(item.getTitle()));
        holder.author.setText(String.valueOf(item.getBy()));
        holder.point.setText(String.valueOf(item.getScore()));
        List kids = item.getKids();
        if(kids != null){
            int size = kids.size();
            holder.comment.setText(size + " comments");
        }

        Date dateTime = new Date(item.getTime() * 1000L);
        CharSequence time = DateUtils.getRelativeDateTimeString(mContext, dateTime.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0);
        holder.time.setText(time);

        final String url = item.getUrl();
        if(url != null){
            holder.urlButton.setText(url.toLowerCase());
        }
        holder.urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.BUS.post(new OpenUrlEvent(url));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public HackerNewsItem get(int position) {
        return mDataset.get(position);
    }

    public int add(HackerNewsItem item) {
        return mDataset.add(item);
    }

    public int indexOf(HackerNewsItem item) {
        return mDataset.indexOf(item);
    }

    public void updateItemAt(int index, HackerNewsItem item) {
        mDataset.updateItemAt(index, item);
    }

    public void addAll(List<HackerNewsItem> items) {
        mDataset.beginBatchedUpdates();
        for (HackerNewsItem item : items) {
            mDataset.add(item);
        }
        mDataset.endBatchedUpdates();
    }

    public void addAll(HackerNewsItem[] items) {
        addAll(Arrays.asList(items));
    }

    public boolean remove(HackerNewsItem item) {
        return mDataset.remove(item);
    }

    public HackerNewsItem removeItemAt(int index) {
        return mDataset.removeItemAt(index);
    }

    public void clear() {
        mDataset.beginBatchedUpdates();
        //remove items at end, to avoid unnecessary array shifting
        while (mDataset.size() > 0) {
            mDataset.removeItemAt(mDataset.size() - 1);
        }
        mDataset.endBatchedUpdates();
    }

}

