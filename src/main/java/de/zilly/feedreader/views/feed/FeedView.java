package de.zilly.feedreader.views.feed;

import com.apptasticsoftware.rssreader.RssReader;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Simple Feed Reader")
@Route("")
public class FeedView extends VerticalLayout {

    private final RssReader rssReader = new RssReader();
    private final UrlValidator urlValidator = new UrlValidator();
    private final TextField feedUrlField = new TextField("Feed URL");
    private final Button loadButton = new Button("Load");
    private final Grid<FeedItem> grid = new Grid<>();

    public FeedView() {
        addClassName("feed-view");
        setSizeFull();

        feedUrlField.setWidth("100%");
        feedUrlField.setValue("https://www.tagesschau.de/xml/rss2/");

        loadButton.addClickListener(event -> loadRssFeed(feedUrlField.getValue()));

        grid.setHeight("100%");
        grid.addComponentColumn(item -> createFeedCard(item));
        add(feedUrlField, loadButton, grid);
    }

    private void loadRssFeed(String feedUrl) {
        try {
            if (StringUtils.isEmpty(feedUrl) || !urlValidator.isValid(feedUrl)) {
                Notification.show("No VALID URL given!!!", 3000, Notification.Position.MIDDLE);
                return;
            }

            List<FeedItem> feedItems = rssReader.read(feedUrl)
                    .map(e -> new FeedItem(e.getTitle().orElse("Kein Titel"), e.getLink().orElse(""),
                            e.getDescription().filter(d -> d.length() < 250)
                                    .orElse("Text nicht vorhanden oder zu lange"),
                            e.getPubDate().orElse("")))
                    .collect(Collectors.toList());

            grid.setItems(feedItems);
            Notification.show("Feed URL is loaded", 3000, Notification.Position.TOP_CENTER);
        } catch (IOException e) {
            Notification.show("Feed URL could not be loaded", 3000, Notification.Position.TOP_CENTER);
        }
    }

    private HorizontalLayout createFeedCard(FeedItem feedItem) {
        HorizontalLayout card = new HorizontalLayout();
        card.setClassName("card");
        card.setSpacing(false);

        VerticalLayout content = new VerticalLayout();
        content.addClassName("header");
        content.setSpacing(false);
        content.getThemeList().add("spacing-s");

        Span title = new Span(feedItem.getTitle());
        title.addClassName("title");

        Span pubDate = new Span(feedItem.getPublishedDate());
        pubDate.addClassName("date");

        Span description = new Span(feedItem.getDescription());
        description.addClassName("post");

        content.add(title);
        content.add(description);
        content.add(pubDate);

        card.add(content);
        return card;
    }

}
