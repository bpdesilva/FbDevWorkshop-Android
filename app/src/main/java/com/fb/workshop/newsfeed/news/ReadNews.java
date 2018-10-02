package com.fb.workshop.newsfeed.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fb.workshop.newsfeed.GetJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadNews extends AsyncTask<Void, Void, Void> {

    public DatabaseHandler db;
    Context context;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    String address;
    boolean isNetwork;
    List<OfflineItem> offlineItems;
    ProgressDialog pd;

    public ReadNews(Context context, RecyclerView recyclerView, String address) {
        this.address = address;
        this.recyclerView = recyclerView;
        this.context = context;
        db = new DatabaseHandler(context);
        offlineItems = new ArrayList<OfflineItem>();
        offlineItems = db.getAllData(address);
        isNetwork = isNetworkAvailable(context);
        pd = new ProgressDialog(context);
        pd.setMessage("Loading news");
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.d("Network There", String.valueOf(isNetwork));

        if (isNetwork) {
            ProcessJSON(GetData());
        } else {
            GetDBEntries();
        }

        return null;
    }

    private void GetDBEntries() {
        if (offlineItems != null) {

            Log.d("Offline Items", "NOT NULL");

            feedItems = new ArrayList<>();

            for (int i = 0; i < offlineItems.size(); i++) {
                FeedItem feedItem = new FeedItem();

                String title = offlineItems.get(i).getTitle();
                String pubDate = offlineItems.get(i).getPubDate();
                String link = offlineItems.get(i).getLink();
                String description = offlineItems.get(i).getDescription();
                String thumburl = offlineItems.get(i).getThumbURL();

                feedItem.setTitle(title);
                feedItem.setPubDate(pubDate);
                feedItem.setLink(link);
                feedItem.setDescription(description);
                feedItem.setThumbURL(thumburl);

                feedItems.add(feedItem);
            }

        } else {
            Log.d("Offline Items", "NULL");
        }
    }

    private void ProcessJSON(String jsonstring) {

        feedItems = new ArrayList<>();
        String rsslink = address;

        Log.d("JSON String", jsonstring);


        if (jsonstring != null) {

            db.RemoveFav(address);

            try {

                JSONObject jsonObj = new JSONObject(jsonstring);
                JSONArray reviews = jsonObj.getJSONArray("articles");

                for (int i = 0; i < reviews.length(); i++) {

                    JSONObject c = reviews.getJSONObject(i);

                    String title = c.getString("title");
                    String desc = c.getString("description");
                    String thumb = c.getString("urlToImage");
                    String pubDate = c.getString("publishedAt");
                    String url = c.getString("url");

                    FeedItem adata = new FeedItem();

                    adata.setTitle(title);
                    adata.setDescription(desc);
                    adata.setThumbURL(thumb);
                    adata.setPubDate(pubDate);
                    adata.setLink(url);

                    feedItems.add(adata);
                    db.AddtoFavorite(new OfflineItem(title, desc, pubDate, url, thumb, rsslink));
                }

            } catch (final JSONException e) {

                Log.e("Json parsing error: ", e.getMessage());

            }

        }
    }

    public String GetData() {

        String url = address;

        GetJSON getJSON = new GetJSON();

        String jsonStr = getJSON.getJSON(url, 5000);

        return jsonStr;

    }

    @Override
    protected void onPreExecute() {
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        super.onPostExecute(aVoid);
        NewsAdapter myAdapter = new NewsAdapter(context, feedItems, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(myAdapter);
    }
}
