package com.example.i190417_i190468_i190260.Models;

import android.media.Image;

public class Exercise {
    String title, time, toughness;
    Image image;

    public Exercise(String title, String time, String toughness) {
        this.title = title;
        this.time = time;
        this.toughness = toughness;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
