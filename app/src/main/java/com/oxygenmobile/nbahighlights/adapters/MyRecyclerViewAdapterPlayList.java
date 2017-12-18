package com.oxygenmobile.nbahighlights.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.model.PlayListItem;

/**
 * Created by MUSTAFA on 18.12.2017.
 *
 */

public class MyRecyclerViewAdapterPlayList extends RecyclerView
        .Adapter<MyRecyclerViewAdapterPlayList
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static List<PlayListItem> mDataset;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("Deneme", "onClick " + getLayoutPosition() + " " + mDataset.get(getLayoutPosition()).getTitle());
           // myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public MyRecyclerViewAdapterPlayList(List<PlayListItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getTitle());
        holder.dateTime.setText(mDataset.get(position).getPublishedAt());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
