package com.fb.workshop.newsfeed;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fb.workshop.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NewsDetail extends AppCompatActivity {

    TextView detail_title;
    ImageView detail_image;
    WebView detail_webview;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_detail);

        detail_webview = findViewById(R.id.detail_webview);
        detail_title = findViewById(R.id.detail_title);
        detail_image = findViewById(R.id.detail_image);

        Bundle bundle = getIntent().getExtras();

        detail_title.setText(bundle.getString("title"));
        url = bundle.getString("url");

        Document description_doc = Jsoup.parse(bundle.getString("description"));
        description_doc.select("img").remove();
        String description_str = description_doc.toString();

        detail_webview.setBackgroundColor(Color.parseColor("#fafafa"));
        detail_webview.loadData(description_str, "text/html; charset=UTF-8", null);

        if (bundle.getString("image").equalsIgnoreCase("none")) {
            Picasso.with(getApplicationContext()).load(R.drawable.sample).into(detail_image);
        } else {
            Picasso.with(getApplicationContext()).load(bundle.getString("image")).into(detail_image);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_share:

                //ADD SHARE FUNCTIONALITY
                ///////

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);

        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;

    }


}