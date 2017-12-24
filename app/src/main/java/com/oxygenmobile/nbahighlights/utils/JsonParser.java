package com.oxygenmobile.nbahighlights.utils;

import android.util.Log;

import com.oxygenmobile.nbahighlights.configuration.Config;
import com.oxygenmobile.nbahighlights.model.Game;
import com.oxygenmobile.nbahighlights.model.PlayListItem;

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

/**
 * Created by MUSTAFA on 24.12.2017.
 *
 */

public class JsonParser {


    private List<PlayListItem> playListItemsList;
    private String baseUrl;

    public JsonParser(String baseUrl) {
        this.baseUrl = baseUrl;
        this.playListItemsList = new ArrayList<>();
    }

    public JsonParser() {

    }

    public List<PlayListItem> getPlayListItemsList() {
        return playListItemsList;
    }

    public void setPlayListItemsList(List<PlayListItem> playListItemsList) {
        this.playListItemsList = playListItemsList;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<PlayListItem> getJsonData(String callApi){
        String nextPageToken = null;

        try {
            URL url = new URL(baseUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();

            JSONObject object = (JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
            JSONObject subObject = object.getJSONObject("pageInfo");
            double totalResults = subObject.getDouble("totalResults");
            double roundOfTotalResults = Math.ceil(totalResults / Config.RESULTS_PER_PAGE);
            urlConnection.disconnect();
            Log.e("Yuvarlanmis sonuc", String.valueOf((int) roundOfTotalResults));
            int silinecekSayac = 0;
            for (int j = 0; j < roundOfTotalResults; j++) {
                URL url1 = new URL(baseUrl);
                urlConnection = (HttpURLConnection) url1.openConnection();
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder1 = new StringBuilder();
                String line1;
                while ((line1 = bufferedReader1.readLine()) != null) {
                    stringBuilder1.append(line1).append("\n");
                }
                bufferedReader.close();

                JSONObject object1 = (JSONObject) new JSONTokener(stringBuilder1.toString()).nextValue();
                if (object1.has("nextPageToken")) {
                    nextPageToken = object1.getString("nextPageToken");
                }
                JSONArray itemsArray = object1.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonObject = itemsArray.getJSONObject(i); // herbir arrayin icindeki object aliniyor.
                    JSONObject snippet = jsonObject.getJSONObject("snippet");
                    String id = jsonObject.getString("id");
                    String publishedAt = snippet.getString("publishedAt");
                    String title = snippet.getString("title");
                    String thumbnails = snippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");
                    silinecekSayac = silinecekSayac + 1;
                    Log.e("Api den gelen cevap", id + " " + publishedAt + " " + title + " " + thumbnails);
                    this.playListItemsList.add(new PlayListItem(id, publishedAt, title, thumbnails));
                }

                baseUrl = callApi + "&pageToken=" + nextPageToken;
            }
            Log.e("Silinecek Sayac", String.valueOf(silinecekSayac));
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.playListItemsList;
    }


}
