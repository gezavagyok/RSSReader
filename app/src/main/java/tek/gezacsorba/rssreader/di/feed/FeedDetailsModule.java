package tek.gezacsorba.rssreader.di.feed;

import dagger.Module;
import dagger.Provides;
import tek.gezacsorba.rssreader.feed.details.FeedDetailsPresenter;

/**
 * Created by geza on 11/8/17.
 */
@Module
public class FeedDetailsModule {
    @Provides
    FeedDetailsPresenter providePresenter() {
        return new FeedDetailsPresenter();
    }
}
