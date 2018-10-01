package com.fb.workshop.newsfeed;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fb.workshop.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NewsDetail extends AppCompatActivity {

    TextView detail_title, detail_date;
    ImageView detail_image;
    WebView detail_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_detail);

        detail_webview = (WebView) findViewById(R.id.detail_webview);
        detail_title = (TextView) findViewById(R.id.detail_title);
        detail_date = (TextView) findViewById(R.id.detail_date);
        detail_image = (ImageView) findViewById(R.id.detail_image);

        Bundle bundle = getIntent().getExtras();

        detail_title.setText(bundle.getString("title"));
        detail_date.setText(bundle.getString("date"));

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

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#FFFFFF"));
        collapsingToolbarLayout.setTitle(bundle.getString("title"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}