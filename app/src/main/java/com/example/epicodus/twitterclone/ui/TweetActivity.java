package com.example.epicodus.twitterclone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.epicodus.twitterclone.R;
import com.example.epicodus.twitterclone.models.Response;
import com.example.epicodus.twitterclone.models.Tweet;
import com.example.epicodus.twitterclone.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class TweetActivity extends ListActivity {
    private Tweet mTweet;
    private TextView mTweetContentTextView;
    private TextView mNameLabel;
    private TextView mDateLabel;

    private ArrayList<String> mResponses;
    private ArrayAdapter<String> mAdapter;
    private Button mNewResponseButton;
    private EditText mNewResponse;
    //private long mResponseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mTweetContentTextView = (TextView) findViewById(R.id.tweetView);
        mNameLabel = (TextView) findViewById(R.id.nameLabel);
        mDateLabel = (TextView) findViewById(R.id.dateLabel);



        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        mTweetContentTextView.setText(bundle.getString("content"));
        mNameLabel.setText("By:" + bundle.getString("user"));
        mDateLabel.setText(formattedTime(bundle.getLong("createdat")));

        mTweet = Tweet.find(bundle.getString("content"));

        mNewResponseButton = (Button) findViewById(R.id.responseSubmitButton);
        mNewResponse = (EditText) findViewById(R.id.newResponseEdit);

        mResponses = new ArrayList<>();
        for (Response response : mTweet.responses()) {
            mResponses.add(response.getDescription() + " " + formattedTime(response.getCreatedAt()));
            //mResponses.add(formattedTime(response.getCreatedAt()));
        }

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mResponses);
        setListAdapter(mAdapter);

        mNewResponseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addResponse();
            }
        });
    }

    private void addResponse() {
        String description = mNewResponse.getText().toString();
        Response newResponse = new Response(description, mTweet);
        newResponse.save();
        mResponses.add(description +" "+ formattedTime(newResponse.getCreatedAt()));
        //mResponses.add(formattedTime(newResponse.getCreatedAt()));
        mAdapter.notifyDataSetChanged();
        mNewResponse.setText("");
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private String formattedTime(long createdat) {
        Context context = MainActivity.mAdapter.getContext();
        SimpleDateFormat formatter = new SimpleDateFormat(context.getString(R.string.formatted_time));
        formatter.setTimeZone(TimeZone.getTimeZone(context.getString(R.string.timezone)));
        return formatter.format(createdat);

    }


}
