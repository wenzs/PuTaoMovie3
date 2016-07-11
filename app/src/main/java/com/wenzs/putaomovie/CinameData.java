package com.wenzs.putaomovie;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 20private String 6/6/29.
 */
public class CinameData implements Serializable {
    private long id;
    private String cinemaid;
    private String cinemaname;
    private String englishname;
    private String logo;
    private String citycode;
    private String cityname;
    private String countycode;
    private String countyname;
    private String address;
    private String longitude;
    private String latitude;
    private String cs;
    private String generalmark;
    private String popcorn;
    private String countdes;
    private int cpcount;
    private String pricerange;
    private long lowmovieid;
    private String supportgoods;
    private String todayOpis;
    private CinemaMoviePrice[] movieprice;
    private double distance;
    private double stepPrice;

    public Double getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(Double stepPrice) {
        this.stepPrice = stepPrice;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(String cinemaid) {
        this.cinemaid = cinemaid;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountycode() {
        return countycode;
    }

    public void setCountycode(String countycode) {
        this.countycode = countycode;
    }

    public String getCountyname() {
        return countyname;
    }

    public void setCountyname(String countyname) {
        this.countyname = countyname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getGeneralmark() {
        return generalmark;
    }

    public void setGeneralmark(String generalmark) {
        this.generalmark = generalmark;
    }

    public String getPopcorn() {
        return popcorn;
    }

    public void setPopcorn(String popcorn) {
        this.popcorn = popcorn;
    }

    public String getCountdes() {
        return countdes;
    }

    public void setCountdes(String countdes) {
        this.countdes = countdes;
    }

    public int getCpcount() {
        return cpcount;
    }

    public void setCpcount(int cpcount) {
        this.cpcount = cpcount;
    }

    public String getPricerange() {
        return pricerange;
    }

    public void setPricerange(String pricerange) {
        this.pricerange = pricerange;
    }

    public long getLowmovieid() {
        return lowmovieid;
    }

    public void setLowmovieid(long lowmovieid) {
        this.lowmovieid = lowmovieid;
    }

    public String getSupportgoods() {
        return supportgoods;
    }

    public void setSupportgoods(String supportgoods) {
        this.supportgoods = supportgoods;
    }

    public String getTodayOpis() {
        return todayOpis;
    }

    public void setTodayOpis(String todayOpis) {
        this.todayOpis = todayOpis;
    }

    public CinemaMoviePrice[] getMovieprice() {
        return movieprice;
    }

    public void setMovieprice(CinemaMoviePrice[] movieprice) {
        this.movieprice = movieprice;
    }

    @Override
    public String toString() {
        return "CinameData{" +
                "id=" + id +
                ", cinemaid='" + cinemaid + '\'' +
                ", cinemaname='" + cinemaname + '\'' +
                ", englishname='" + englishname + '\'' +
                ", logo='" + logo + '\'' +
                ", citycode='" + citycode + '\'' +
                ", cityname='" + cityname + '\'' +
                ", countycode='" + countycode + '\'' +
                ", countyname='" + countyname + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", cs='" + cs + '\'' +
                ", generalmark='" + generalmark + '\'' +
                ", popcorn='" + popcorn + '\'' +
                ", countdes='" + countdes + '\'' +
                ", cpcount=" + cpcount +
                ", pricerange='" + pricerange + '\'' +
                ", lowmovieid=" + lowmovieid +
                ", supportgoods='" + supportgoods + '\'' +
                ", todayOpis='" + todayOpis + '\'' +
                ", movieprice=" + Arrays.toString(movieprice) +
                '}';
    }
}
