package com.oxygenmobile.nbahighlights.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.adapters.MyAllGamesAdapter;
import com.oxygenmobile.nbahighlights.configuration.Config;
import com.oxygenmobile.nbahighlights.model.Game;
import com.oxygenmobile.nbahighlights.model.PlayListItem;
import com.oxygenmobile.nbahighlights.other.CircleTransform;
import com.oxygenmobile.nbahighlights.utils.GlobalVariables;
import com.oxygenmobile.nbahighlights.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayListItemsActivity extends AppCompatActivity {
    private ImageView backroundPhoto;
    private ListView listView;
    private List<Game> gameList = new ArrayList<>();
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_items);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewTitle = findViewById(R.id.textViewTitle);

        backroundPhoto = findViewById(R.id.backroundPhoto);
        listView = findViewById(R.id.listview);

        Intent intent = getIntent();
        int playListPosition = intent.getIntExtra("PlayListID", 0);//async olarak bu id ile api cagir

        PlayListItem playListItem = ((GlobalVariables) getApplicationContext()).getPlayListItemList().get(playListPosition);
        textViewTitle.setText(playListItem.getTitle());

        Glide.with(this).load(Utils.allGameUpperImage)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(backroundPhoto);



        RetrievePlayListAllGameAsyncTask retrievePlayListAllGameAsyncTask = new RetrievePlayListAllGameAsyncTask();
        retrievePlayListAllGameAsyncTask.execute(Config.PLAY_LIST_GAMES_API + playListItem.getId());


        // Log.e("Tıklanan activity", playListItem.getId());
    }


    private class RetrievePlayListAllGameAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            Log.e("from doinbackround", strings[0]);


            URL url = null;
            try {
                url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                try {
                    JSONObject object = (JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
                    JSONArray itemsArray = object.getJSONArray("items");

                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject eachGame = itemsArray.getJSONObject(i);
                        JSONObject snippet = eachGame.getJSONObject("snippet");
                        String title = snippet.getString("title");
                        String description = snippet.getString("description");
                        String thumbnails = snippet.getJSONObject("thumbnails").getJSONObject("standard").getString("url");
                        JSONObject resourceId = snippet.getJSONObject("resourceId");
                        String videoId = resourceId.getString("videoId");


                        Game game = new Game(title, description, thumbnails, videoId);
                        gameList.add(game);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MyAllGamesAdapter customAdapter = new MyAllGamesAdapter(getApplicationContext(), gameList);
            listView.setAdapter(customAdapter);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
