package com.oxygenmobile.nbahighlights.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.activity.YoutubeVideoDisplayActivity;
import com.oxygenmobile.nbahighlights.model.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 * Created by MUSTAFA on 21.12.2017.
 */

public class MyAllGamesAdapter extends BaseAdapter {

    private List<Game> gameList;
    private Context context;


    public MyAllGamesAdapter(Context context, List<Game> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int i) {
        return gameList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return gameList.indexOf(gameList.get(i));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.game_row, null);

        }
        ImageView gamePhoto;
        TextView gameName;


        gamePhoto = convertView.findViewById(R.id.gamePhoto);
        gameName = convertView.findViewById(R.id.gameName);


        Picasso.with(context).load(gameList.get(position).getThumbnails()).into(gamePhoto);
        gameName.setText(this.separateTitleOfGame(gameList.get(position).getTitle()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), YoutubeVideoDisplayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("videoID", gameList.get(position).getVideoId());
                view.getContext().startActivity(intent);
                Log.e("video id", gameList.get(position).getVideoId());
            }
        });


        return convertView;
    }

    private String separateTitleOfGame(String gameOfTitle) {
        String title = gameOfTitle;
        String[] splitOf = gameOfTitle.split(" ");


        for (int i = 0; i < splitOf.length; i++) {
            if (splitOf[i].equalsIgnoreCase("@")) {
                Log.e("takımlar", splitOf[i - 1] + " - " + splitOf[i + 1]);
                title = splitOf[i - 1] + " - " + splitOf[i + 1];
                break;
            }
        }


        return title;
    }
}
