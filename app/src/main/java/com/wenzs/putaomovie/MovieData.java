package com.wenzs.putaomovie;

import java.io.Serializable;

/**
 * Created by Administrator on 20private String 6/6/29.
 */
public class MovieData implements Serializable{
    private long id;
    private String movieid;
    private String moviename;
    private String language;
    private String type;
    private String director;
    private String actors;
    private String length;
    private String highlight;
    private String logo;
    private long releasedate;
    private String generalmark;
    private String gcedition;
    private String state;
    private String englishname;
    private String todayOpis;
    private String videoUrl;
    private String content;

    public String getStill() {
        return still;
    }

    public void setStill(String still) {
        this.still = still;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String still;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public long getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(long releasedate) {
        this.releasedate = releasedate;
    }

    public String getGeneralmark() {
        return generalmark;
    }

    public void setGeneralmark(String generalmark) {
        this.generalmark = generalmark;
    }

    public String getGcedition() {
        return gcedition;
    }

    public void setGcedition(String gcedition) {
        this.gcedition = gcedition;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getTodayOpis() {
        return todayOpis;
    }

    public void setTodayOpis(String todayOpis) {
        this.todayOpis = todayOpis;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "id=" + id +
                ", movieid='" + movieid + '\'' +
                ", moviename='" + moviename + '\'' +
                ", language='" + language + '\'' +
                ", type='" + type + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", length='" + length + '\'' +
                ", highlight='" + highlight + '\'' +
                ", logo='" + logo + '\'' +
                ", releasedate=" + releasedate +
                ", generalmark='" + generalmark + '\'' +
                ", gcedition='" + gcedition + '\'' +
                ", state='" + state + '\'' +
                ", englishname='" + englishname + '\'' +
                ", todayOpis='" + todayOpis + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", content='" + content + '\'' +
                ", still='" + still + '\'' +
                '}';
    }
}