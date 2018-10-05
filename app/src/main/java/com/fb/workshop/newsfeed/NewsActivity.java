package com.fb.workshop.newsfeed;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fb.workshop.MainActivity;
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

        Intent intent = getIntent();
        String interestlist = intent.getStringExtra("interests");

        readRSS = new ReadNews(NewsActivity.this, recyclerView, "\n" +
                "https://newsapi.org/v2/everything?q=" + Uri.encode(interestlist) + "&from=2018-09-04&sortBy=publishedAt&apiKey=daa06f7e6b084d0bb95a4512483aff03");
        readRSS.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_logout:

                //Logout from Facebook
                ////////////////////////

                //End current activity and back to home.
                Intent i = new Intent(NewsActivity.this, MainActivity.class);
                startActivity(i);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
