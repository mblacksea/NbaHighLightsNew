package com.oxygenmobile.nbahighlights.utils;

import android.app.Application;

import java.util.List;

import com.oxygenmobile.nbahighlights.model.PlayListItem;

/**
 * Created by MUSTAFA on 18.12.2017.
 *
 */

public class GlobalVariables extends Application {
    private List<PlayListItem> playListItemList;

    public void setPlayListItemList(List<PlayListItem> playListItemList) {
        this.playListItemList = playListItemList;
    }

    public List<PlayListItem> getPlayListItemList() {
        return playListItemList;
    }


}
