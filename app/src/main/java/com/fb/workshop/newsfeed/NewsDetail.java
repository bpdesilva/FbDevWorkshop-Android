package com.fb.workshop.newsfeed;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.fb.workshop.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NewsDetail extends AppCompatActivity {

    TextView detail_title;
    ImageView detail_image;
    WebView detail_webview;
    String url;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_detail);


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            } });


        detail_webview = (WebView) findViewById(R.id.detail_webview);
        detail_title = (TextView) findViewById(R.id.detail_title);
        detail_image = (ImageView) findViewById(R.id.detail_image);

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
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(url))
                            .build();
                    shareDialog.show(linkContent);
                }
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