package com.spider.jsoup;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @author pengmin
 * @date 2021/4/2 1:10
 */

public class UserInfo implements Serializable {
    @ExcelIgnore
    private int id;
    @ExcelProperty("username")
    private String username;
    @ExcelProperty("facebook")
    private String facebook;
    @ExcelProperty("twitter")
    private String twitter;
    @ExcelProperty("pinterest")
    private String pinterest;
    @ExcelProperty("instagram")
    private String instagram;
    @ExcelProperty("youtube")
    private String youtube;

    public UserInfo(String username) {
        this.username = username;
    }

    public UserInfo(int id, String username, String facebook, String twitter, String pinterest, String instagram, String youtube) {
        this.id = id;
        this.username = username;
        this.facebook = facebook;
        this.twitter = twitter;
        this.pinterest = pinterest;
        this.instagram = instagram;
        this.youtube = youtube;
    }

    public UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                ", pinterest='" + pinterest + '\'' +
                ", instagram='" + instagram + '\'' +
                ", youtube='" + youtube + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getPinterest() {
        return pinterest;
    }

    public void setPinterest(String pinterest) {
        this.pinterest = pinterest;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
