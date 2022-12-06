package com.example.i190417_i190468_i190260.Models;

public class Exercise {
    String Name, Link, Description, Calories, Time, Image;

    public Exercise() {}

    public Exercise(String name, String link, String description, String calories, String time, String image) {
        Name = name;
        Link = link;
        Description = description;
        Calories = calories;
        Time = time;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }
}
