package com.oxygenmobile.nbahighlights.model;

import java.io.Serializable;

/**
 * Created by MUSTAFA on 18.12.2017.
 *
 */

public class PlayListItem implements Serializable {

    private String id;
    private String publishedAt;
    private String title;
    private String thumbnails;

    public PlayListItem() {

    }

    public PlayListItem(String id, String publishedAt, String title, String thumbnails) {
        this.id = id;
        this.publishedAt = publishedAt;
        this.title = title;
        this.thumbnails = thumbnails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }
}
