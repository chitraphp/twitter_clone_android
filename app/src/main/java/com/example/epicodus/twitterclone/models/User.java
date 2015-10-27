package com.example.epicodus.twitterclone.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by Guest on 10/27/15.
 */
@Table(name = "Users", id = "_id")
public class User extends Model {

    @Column(name = "Name")
    private String mName;

    public User() {
        super();

    }

    public String getName() {
        return mName;
    }

    public User(String name) {
        super();
        mName = name;
    }


    public static User find(String username) {
        return new Select().from(User.class).where("Name = ?", username).executeSingle();

    }
}
