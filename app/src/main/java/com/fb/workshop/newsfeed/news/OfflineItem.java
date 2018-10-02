package com.fb.workshop.newsfeed.news;

/**
 * Created by Suresh on 4/1/2016.
 */
public class OfflineItem {

    int _id;
    String _title;
    String _description;
    String _pubDate;
    String _link;
    String _thumbURL;
    String _Rsslink;

    public OfflineItem() {

    }

    public OfflineItem(String _Rsslink) {
        this._Rsslink = _Rsslink;
    }

    public OfflineItem(String _title, String _description, String _pubDate, String _link, String _thumbURL, String _Rsslink) {
        this._title = _title;
        this._description = _description;
        this._pubDate = _pubDate;
        this._link = _link;
        this._thumbURL = _thumbURL;
        this._Rsslink = _Rsslink;
    }

    public int getIF() {
        return _id;
    }

    public void setID(int _id) {
        this._id = _id;
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

    public String getRssLink() {
        return _Rsslink;
    }

    public void setRssLink(String _Rsslink) {
        this._Rsslink = _Rsslink;
    }
}
