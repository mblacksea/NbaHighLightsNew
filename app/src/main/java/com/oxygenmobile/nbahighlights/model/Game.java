package com.oxygenmobile.nbahighlights.model;

/**
 * Created by MUSTAFA on 21.12.2017.
 *
 */

public class Game {

    private String title;
    private String description;
    private String thumbnails;
    private String videoId;

    public Game(String title, String description, String thumbnails, String videoId) {
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
        this.videoId = videoId;
    }

    public Game() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
