package com.example.topnews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.topnews.Model.Sources;
import com.example.topnews.R;
import com.example.topnews.interfces.RecyclerClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Sources> sources;
    private RecyclerClickListener mRecyclerClickListener;

    public CategoryRecyclerViewAdapter(List<Sources> sources, RecyclerClickListener mListener) {
        this.sources = sources;
        this.mRecyclerClickListener = mListener;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items2,parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(sources !=null && sources.size()>0) {
            holder.categoryView.setText(sources.get(position).getName());
            holder.categoryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerClickListener.onRecyclerClickListener(sources.get(position).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryView = itemView.findViewById(R.id.catg);
        }
    }
}
