package com.example.topnews.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newslibrary.Articles;
import com.example.topnews.R;
import com.example.topnews.activities.NewsDetails;
import com.example.topnews.interfces.NewsRecyclerClickListner;
import com.example.topnews.models.NewsArticles;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

   private Articles articles1 = new Articles();
   private Context context;
   private List<NewsArticles> articles;
   private NewsRecyclerClickListner newsRecyclerClickListner;

    public NewsRecyclerViewAdapter(List<NewsArticles> articles) {
        this.articles = articles;
        this.newsRecyclerClickListner = newsRecyclerClickListner; //for new activity
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(articles !=null && articles.size()>0) {
            final  NewsArticles a = articles.get(position);
            holder.tvTitle.setText(a.getTitle());
            holder.tvSource.setText(a.getNewsId());
            holder.tvDate.setText(a.getPublishedAt());

            String imageUrl = a.getUrlToImage();
            String  url = a.getUrl();
            Picasso.with(context).load(imageUrl).into(holder.imageView);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewsDetails.class);
                    intent.putExtra("title",a.getTitle());
                    intent.putExtra("source",a.getNewsId());
                    intent.putExtra("time",a.getPublishedAt());
                    intent.putExtra("desc",a.getDescription());
                    intent.putExtra("imageUrl",a.getUrlToImage());
                    intent.putExtra("url",a.getUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvSource,tvDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDate = itemView.findViewById(R.id.tvDate);
           /* BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(Resources.getSystem(),R.id.imagView, options);*/
            imageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
