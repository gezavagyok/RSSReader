package tek.gezacsorba.rssreader.feed;

import android.content.Context;

import tek.gezacsorba.rssreader.architecture.Contract;
import tek.gezacsorba.rssreader.feed.items.FeedItem;
import tek.gezacsorba.rssreader.feed.network.pojo.Channel;

/**
 * Created by geza on 11/5/17.
 */

public interface FeedContract {

    interface Presenter extends Contract.Presenter {
        void loadNextFeed();
        void loadPreviousFeed();
        void loadDetails(Context context);

        void removeSource(ChannelItem url);
        void addSource(String url);
    }

    interface View extends Contract.View {
        void showFeed(FeedItem item);
        void addChannelToMenu(ChannelItem channel);
        void removeMenuItem(ChannelItem item);
    }
}