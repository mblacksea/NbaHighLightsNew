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
import java.util.List;
import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.activity.PlayListItemsActivity;
import com.oxygenmobile.nbahighlights.model.PlayListItem;
import com.squareup.picasso.Picasso;

/**
 * Created by MUSTAFA on 18.12.2017.
 *
 */

public class MyRecyclerViewAdapterPlayList extends RecyclerView.Adapter<MyRecyclerViewAdapterPlayList.DataObjectHolder>  {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<PlayListItem> mDataset;
    private List<PlayListItem> mFilteredList ;
    private Context context;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MyRecyclerViewAdapterPlayList(List<PlayListItem> myDataset, Context context) {
        mDataset = myDataset;
        this.context=context;
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        //    dateTime =  itemView.findViewById(R.id.textView2);

            Log.i(LOG_TAG, "Adding Listener");

        }


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
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        Picasso.with(holder.imageView.getContext()).load(mDataset.get(position).getThumbnails()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Log.e("asd", mDataset.get(position).getId());
                Intent intent = new Intent(context, PlayListItemsActivity.class);
                intent.putExtra("PlayListID",position);
                context.startActivity(intent);
            }
        });
        // holder.label.setImageResource(mDataset.get(position).getThumbnails());
      //  holder.dateTime.setText(mDataset.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
