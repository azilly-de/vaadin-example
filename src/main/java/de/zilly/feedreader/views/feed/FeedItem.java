package de.zilly.feedreader.views.feed;

public class FeedItem {
    private String title;
    private String url;
    private String description;
    private String publishedDate;

    public FeedItem(String title, String url, String description, String publishedDate) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.publishedDate = publishedDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
