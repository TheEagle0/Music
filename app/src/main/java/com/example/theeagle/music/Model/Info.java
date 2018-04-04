package com.example.theeagle.music.Model;

/**
 * Created by theeagle on 3/27/18.
 */

public class Info {
    private String artistName;
    private String trackName;
    private String uri;



    public Info(String artistName, String trackName, String uri) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.uri = uri;

    }

    public Info() {

    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
