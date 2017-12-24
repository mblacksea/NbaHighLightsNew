package com.oxygenmobile.nbahighlights.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.activity.PlayListItemsActivity;
import com.oxygenmobile.nbahighlights.activity.YoutubeVideoDisplayActivity;
import com.oxygenmobile.nbahighlights.model.Game;
import com.oxygenmobile.nbahighlights.model.PlayListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MUSTAFA on 25.12.2017.
 * 
 */


public class MyRecyclerViewAdapterTopPlayList extends RecyclerView
        .Adapter<MyRecyclerViewAdapterTopPlayList
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static List<Game> mDataset;
    Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MyRecyclerViewAdapterTopPlayList(List<Game> myDataset, Context context) {
        mDataset = myDataset;
        this.context=context;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            dateTime =  itemView.findViewById(R.id.textView);

            Log.i(LOG_TAG, "Adding Listener");

        }


    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row_top_plays, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        Picasso.with(holder.imageView.getContext()).load(mDataset.get(position).getThumbnails()).into(holder.imageView);
        holder.dateTime.setText(mDataset.get(position).getTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), YoutubeVideoDisplayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("videoID", mDataset.get(position).getVideoId());
                view.getContext().startActivity(intent);
                Log.e("video id", mDataset.get(position).getVideoId());


            }
        });

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
