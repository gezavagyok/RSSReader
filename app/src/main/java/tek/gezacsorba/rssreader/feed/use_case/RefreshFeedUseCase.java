package tek.gezacsorba.rssreader.feed.use_case;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import tek.gezacsorba.rssreader.feed.FeedDatasource;
import tek.gezacsorba.rssreader.feed.items.FeedItem;

/**
 * Created by geza on 11/7/17.
 */

public class RefreshFeedUseCase {

    FeedDatasource feedDatasource;

    @Inject
    public RefreshFeedUseCase(FeedDatasource feedDatasource) {
        this.feedDatasource = feedDatasource;
    }

    public Observable<List<FeedItem>> execute(){
        return feedDatasource.getFeedList();
    }
}
