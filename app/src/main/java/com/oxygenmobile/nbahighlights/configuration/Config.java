package com.oxygenmobile.nbahighlights.configuration;

/**
 * Created by MUSTAFA on 18.12.2017.
 *
 */

public class Config {

    public static final String YOUTUBE_DEVELOPER_KEY = "AIzaSyAgCDOHtDAaHXYMn49RQRO4s6hFElZ9UBQ";
    //TODO play_list_api nin componentlerini parcala.
    public static final String PLAY_LIST_API = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCy5zlocjbG3OkZOXt7KzLYA&key=AIzaSyAgCDOHtDAaHXYMn49RQRO4s6hFElZ9UBQ&maxResults=50";
    public static final String PLAY_LIST_GAMES_API ="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&key=AIzaSyBeV1Hl89ycHUCFRuv4ymCR42y7QImkuAs&playlistId=";
    public static final String TOP_PLAYS_API =      "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&key=AIzaSyBeV1Hl89ycHUCFRuv4ymCR42y7QImkuAs&playlistId=PLlVlyGVtvuVkUpgWlBV44heQGeVpxFvU4";
    public static final double RESULTS_PER_PAGE = 50.0;

}
