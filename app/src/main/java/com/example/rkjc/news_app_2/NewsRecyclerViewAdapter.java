package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    Context mContext;
    ArrayList<NewsItem> mNews;

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItem> news) {
        this.mContext = context;
        this.mNews = news;
    }

    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        TextView publishedAt;


        public NewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            publishedAt = (TextView) itemView.findViewById(R.id.publishedAt);
        }

        void bind(final int listIndex) {
            title.setText("Title: " + mNews.get(listIndex).getTitle());
            description.setText("Description: " + mNews.get(listIndex).getDescription());
            publishedAt.setText("Date: " + mNews.get(listIndex).getPublishedAt());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urlString = mNews.get(getAdapterPosition()).getUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    mContext.startActivity(browserIntent);
                }
            });
        }
    }
}
