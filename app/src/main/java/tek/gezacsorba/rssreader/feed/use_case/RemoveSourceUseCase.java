package tek.gezacsorba.rssreader.feed.use_case;

import javax.inject.Inject;

import io.reactivex.Single;
import tek.gezacsorba.rssreader.feed.ChannelItem;
import tek.gezacsorba.rssreader.feed.FeedDatasource;

/**
 * Created by geza on 11/7/17.
 */

public class RemoveSourceUseCase {

    FeedDatasource datasource;

    @Inject
    public RemoveSourceUseCase(FeedDatasource datasource) {
        this.datasource = datasource;
    }

    public Single<Boolean> execute(ChannelItem source) {
        datasource.removeSource(source);
        return Single.just(true);
    }
}
