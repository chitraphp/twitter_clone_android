package com.example.epicodus.twitterclone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.epicodus.twitterclone.R;
import com.example.epicodus.twitterclone.adapters.TweetAdapter;
import com.example.epicodus.twitterclone.models.Tweet;
import com.example.epicodus.twitterclone.models.User;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private SharedPreferences mPreferences;
    private User mUser;
    private EditText mTweetText;
    private Button mSubmitButton;
    private ArrayList<Tweet> mTweets;
    public static TweetAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        mTweetText = (EditText) findViewById(R.id.newTweetEdit);
        mSubmitButton = (Button) findViewById(R.id.tweetSubmitButton);
        mTweets = (ArrayList) Tweet.all();

        mAdapter = new TweetAdapter(this, mTweets);
        setListAdapter(mAdapter);

        if(!isRegistered()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = mTweetText.getText().toString();
                Tweet tweet = new Tweet(tweetContent, mUser);
                tweet.save();
                mTweets.add(tweet);
                mAdapter.notifyDataSetChanged();
                //Clears input and hides Keyboard

                mTweetText.setText("'");
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


            }
        });
    }

    private boolean isRegistered() {

        String userName = mPreferences.getString("userName", null);
        if (userName != null) {
            setUser(userName);
            return true;
        } else {

            return false;
        }

    }

    private void setUser(String username) {
        User user = User.find(username);
        if (user != null) mUser = user;
        else {
            mUser = new User(username);
            mUser.save();
        }

        Toast.makeText(this, "welcome" + mUser.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(ListView l,View v, int position, long id) {
        super.onListItemClick(l,v,position,id);
        Tweet thisTweet = (Tweet) mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("content",thisTweet.getContent());
        bundle.putString("user", thisTweet.getUser().getName());
        bundle.putLong("createdat",thisTweet.getCreatedAt());

        Intent intent = new Intent(this,TweetActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
