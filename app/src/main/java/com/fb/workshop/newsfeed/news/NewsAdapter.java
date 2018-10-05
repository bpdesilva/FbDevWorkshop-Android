package com.fb.workshop.newsfeed.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fb.workshop.R;
import com.fb.workshop.newsfeed.NewsDetail;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    ArrayList<FeedItem> feedItems;
    Context context;

    public NewsAdapter(Context context, ArrayList<FeedItem> feedItems, RecyclerView rv) {
        this.feedItems = feedItems;
        this.context = context;
    }

    public static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat("E, d MMMM yyy hh:mm:ss z");

        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final FeedItem current = feedItems.get(position);

        final String formattedDate = (String) DateUtils.getRelativeTimeSpanString(getDateInMillis(current.getPubDate()), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        holder.Title.setText(current.getTitle());
        //holder.Date.setText(formattedDate);
        holder.Date.setText(current.getPubDate());

        if (current.getThumbURL().length() != 0) {
            Picasso.with(context).load(current.getThumbURL()).placeholder(R.drawable.sample).into(holder.ThumbNail);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log to facebook analytics here (article hit stats)
                ///////////



                Intent intent = new Intent(context, NewsDetail.class);
                intent.putExtra("description", current.getDescription());
                intent.putExtra("title", current.getTitle());
                //intent.putExtra("date", formattedDate);
                intent.putExtra("date", current.getPubDate());
                intent.putExtra("image", current.getThumbURL());
                intent.putExtra("url", current.getLink());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (feedItems == null) {
            return 0;
        } else {
            return feedItems.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Date;
        ImageView ThumbNail;
        public MyViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.tvNewsTitle);
            Date = itemView.findViewById(R.id.tvNewsTime);
            ThumbNail = itemView.findViewById(R.id.tvNewsThumb);
        }
    }

}
