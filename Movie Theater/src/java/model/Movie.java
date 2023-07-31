/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author HP
 */
public class Movie {

    private Integer mid;
    private String title;
    private Date redob;
    private Integer duration;
    private Rate rate;
    private Language language;
    private String mdesc;
    private String poster;
    private Integer view;
    private String bg;
    private Show show;
    private String URL;

    public Movie() {
    }

    public Movie(Integer mid) {
        this.mid = mid;
    }

    public Movie(Integer mid, String title, Date redob, Integer duration, Rate rate, Language language, String mdesc, String poster, Integer view, String bg, Show show, String URL) {
        this.mid = mid;
        this.title = title;
        this.redob = redob;
        this.duration = duration;
        this.rate = rate;
        this.language = language;
        this.mdesc = mdesc;
        this.poster = poster;
        this.view = view;
        this.bg = bg;
        this.show = show;
        this.URL = URL;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRedob() {
        return redob;
    }

    public void setRedob(Date redob) {
        this.redob = redob;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getMdesc() {
        return mdesc;
    }

    public void setMdesc(String mdesc) {
        this.mdesc = mdesc;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    

  

}
