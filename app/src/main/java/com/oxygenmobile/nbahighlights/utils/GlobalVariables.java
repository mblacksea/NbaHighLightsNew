package com.oxygenmobile.nbahighlights.utils;

import android.app.Application;

import java.util.List;

import com.oxygenmobile.nbahighlights.model.Game;
import com.oxygenmobile.nbahighlights.model.PlayListItem;

/**
 * Created by MUSTAFA on 18.12.2017.
 */

public class GlobalVariables extends Application {
    private List<PlayListItem> playListItemList;
    private List<Game> topPlayListItem;

    public List<Game> getTopPlayListItem() {
        return topPlayListItem;
    }

    public void setTopPlayListItem(List<Game> topPlayListItem) {
        this.topPlayListItem = topPlayListItem;
    }

    public void setPlayListItemList(List<PlayListItem> playListItemList) {
        this.playListItemList = playListItemList;
    }

    public List<PlayListItem> getPlayListItemList() {
        return playListItemList;
    }


}
