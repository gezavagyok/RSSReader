package tek.gezacsorba.rssreader.feed;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import tek.gezacsorba.rssreader.DateUtil;
import tek.gezacsorba.rssreader.feed.items.FeedItem;
import tek.gezacsorba.rssreader.feed.network.RssService;
import tek.gezacsorba.rssreader.feed.network.pojo.Channel;
import tek.gezacsorba.rssreader.feed.network.pojo.RSS;

/**
 * Created by geza on 11/7/17.
 */

public class FeedDatasource {

    List<String> urls = new ArrayList<>();

    private final RssService services;

    public FeedDatasource(RssService services) {
        this.services = services;
    }

    public Single<ChannelItem> addSource(String url) {
        urls.add(url);
        return Single.fromObservable(services.getFeed(url)).map(rss -> new ChannelItem(rss.getChannel(), url));
    }

    public void removeSource(ChannelItem source) {
        urls.remove(source.getUrl());
    }

    public Observable<List<FeedItem>> getFeedList() {
        urls.add("https://www.nasa.gov/rss/dyn/Houston-We-Have-a-Podcast.rss");
        Observable<RSS> items = services.getFeed("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss");
        for (String url : urls) {
            items = items.concatWith(services.getFeed(url));
        }
        return items.map(rss -> new ArrayList<>(rss.getChannel().getItemList()));
    }
}
