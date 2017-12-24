package com.oxygenmobile.nbahighlights.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.configuration.Config;
import com.oxygenmobile.nbahighlights.model.PlayListItem;
import com.oxygenmobile.nbahighlights.utils.GlobalVariables;
import com.oxygenmobile.nbahighlights.utils.JsonParser;


public class SplashActivity extends AppCompatActivity {
    private String baseUrl = Config.PLAY_LIST_API;
    private String nextPageToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RetrievePlayListAsyncTask retrievePlayListAsyncTask = new RetrievePlayListAsyncTask();
        retrievePlayListAsyncTask.execute();

    }


    private class RetrievePlayListAsyncTask extends AsyncTask<Void, Void, List<PlayListItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<PlayListItem> doInBackground(Void... voids) {

            JsonParser jsonParser = new JsonParser(baseUrl);

            ((GlobalVariables) getApplicationContext()).setPlayListItemList(jsonParser.getJsonData(Config.PLAY_LIST_API));

            return null;
        }

        @Override
        protected void onPostExecute(List<PlayListItem> playLists) {
            super.onPostExecute(playLists);


            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // this code will be executed after 2 seconds
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);


        }
    }
}