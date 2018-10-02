package com.fb.workshop.newsfeed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.fb.workshop.R;
import com.fb.workshop.newsfeed.news.ReadNews;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ReadNews readRSS;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest);

        recyclerView = findViewById(R.id.news_list);

        readRSS = new ReadNews(NewsActivity.this, recyclerView, "https://newsapi.org/v2/everything?q=bitcoin&from=2018-09-02&sortBy=publishedAt&apiKey=daa06f7e6b084d0bb95a4512483aff03");
        readRSS.execute();
    }


}
