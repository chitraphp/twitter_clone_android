package com.example.epicodus.twitterclone.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Guest on 10/28/15.
 */

@Table(name = "Responses", id = "_id")
public class Response extends Model {

    @Column(name = "Description")
    private String mDescription;

    @Column(name = "CreatedAt")
    private long mCreatedAt;

    @Column(name = "Tweet")
    private Tweet mTweet;

    public Response() {
        super();
    }

    public Response(String description, Tweet tweet) {
        mDescription = description;
        mCreatedAt = new Date().getTime();
        mTweet = tweet;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(long createdAt) {
        mCreatedAt = createdAt;
    }

    public Tweet getTweet() {
        return mTweet;
    }

    public void setTweet(Tweet tweet) {
        mTweet = tweet;
    }


}
