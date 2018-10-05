package com.fb.workshop.newsfeed.news;

/**
 * Created by Suresh on 4/1/2016.
 */
public class FeedItem {

    String _title;
    String _description;
    String _pubDate;
    String _link;
    String _thumbURL;

    public FeedItem() {

    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public String getPubDate() {
        return _pubDate;
    }

    public void setPubDate(String pubDate) {
        this._pubDate = pubDate;
    }

    public String getLink() {
        return _link;
    }

    public void setLink(String link) {
        this._link = link;
    }

    public String getThumbURL() {
        return _thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this._thumbURL = thumbURL;
    }

}
