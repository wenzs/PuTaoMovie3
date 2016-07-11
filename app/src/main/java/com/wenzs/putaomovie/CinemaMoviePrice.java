package com.wenzs.putaomovie;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/29.
 */
public class CinemaMoviePrice implements Serializable{
    private long ptMovieId;
    private long moviePrice;

    public long getMoviePrice() {
        return moviePrice;
    }

    public void setMoviePrice(long moviePrice) {
        this.moviePrice = moviePrice;
    }

    public long getPtMovieId() {
        return ptMovieId;
    }

    public void setPtMovieId(long ptMovieId) {
        this.ptMovieId = ptMovieId;
    }

    @Override
    public String toString() {
        return "CinemaMoviePrice{" +
                "ptMovieId=" + ptMovieId +
                ", moviePrice=" + moviePrice +
                '}';
    }
}
