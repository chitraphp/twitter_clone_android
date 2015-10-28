package com.example.epicodus.twitterclone.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.epicodus.twitterclone.R;
import com.example.epicodus.twitterclone.models.Tweet;
import com.example.epicodus.twitterclone.models.User;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TweetActivity extends AppCompatActivity {
    private Tweet mTweet;
    private TextView mTweetContentTextView;
    private TextView mNameLabel;
    private TextView mDateLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mTweetContentTextView = (TextView) findViewById(R.id.tweetContentTextView);
        mNameLabel = (TextView) findViewById(R.id.nameLabel);
        mDateLabel = (TextView) findViewById(R.id.dateLabel);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        mTweetContentTextView.setText(bundle.getString("content"));
        mNameLabel.setText("By:" + bundle.getString("user"));
        mDateLabel.setText(formattedTime(bundle.getLong("createdat")));

    }

    private String formattedTime(long createdat) {
        Context context = MainActivity.mAdapter.getContext();
        SimpleDateFormat formatter = new SimpleDateFormat(context.getString(R.string.formatted_time));
        formatter.setTimeZone(TimeZone.getTimeZone(context.getString(R.string.timezone)));
        return formatter.format(createdat);

    }


}
