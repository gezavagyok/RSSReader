package tek.gezacsorba.rssreader.feed.use_case;

import javax.inject.Inject;

import io.reactivex.Single;
import tek.gezacsorba.rssreader.feed.ChannelItem;
import tek.gezacsorba.rssreader.feed.FeedDatasource;
import tek.gezacsorba.rssreader.feed.network.pojo.Channel;

/**
 * Created by geza on 11/7/17.
 */

public class AddSourceUseCase {

    FeedDatasource datasource;

    @Inject
    public AddSourceUseCase(FeedDatasource datasource) {
        this.datasource = datasource;
    }

    public Single<ChannelItem> execute(String newSource) {
        return datasource.addSource(newSource);
    }
}
