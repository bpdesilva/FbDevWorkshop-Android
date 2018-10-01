package com.fb.workshop.newsfeed.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadNews extends AsyncTask<Void, Void, Void> {

    public DatabaseHandler db;
    Context context;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    String address;
    URL url;
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
            ProcessXML(GetData());
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

    private void ProcessXML(Document data) {
        if (data != null) {

            db.RemoveFav(address);

            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();

            String title, desc, pubdate, link, rsslink, thumbURL;

            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);

                title = "";
                desc = "";
                pubdate = "";
                link = "";
                thumbURL = "";

                rsslink = address;

                if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem feedItem = new FeedItem();
                    NodeList itemChilds = currentChild.getChildNodes();
                    for (int j = 0; j < itemChilds.getLength(); j++) {
                        Node current = itemChilds.item(j);

                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            feedItem.setTitle(current.getTextContent());
                            title = current.getTextContent();
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            feedItem.setPubDate(current.getTextContent());
                            pubdate = current.getTextContent();
                        } else if (current.getNodeName().equalsIgnoreCase("link")) {
                            feedItem.setLink(current.getTextContent());
                            link = current.getTextContent();
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            feedItem.setDescription(current.getTextContent());
                            desc = current.getTextContent();

                            Matcher matcher = Pattern.compile("src=\"([^\"]+)").matcher(current.getTextContent());

                            if (matcher.find()) {
                                feedItem.setThumbURL(matcher.group(1));
                                thumbURL = matcher.group(1);
                            } else {
                                feedItem.setThumbURL("none");
                                thumbURL = "none";
                            }
                        }
                    }

                    db.AddtoFavorite(new OfflineItem(title, desc, pubdate, link, thumbURL, rsslink));
                    feedItems.add(feedItem);
                }
            }

        }
    }


    public Document GetData() {
        HttpURLConnection connection = null;
        ;
        try {
            url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            connection.disconnect();
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
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
