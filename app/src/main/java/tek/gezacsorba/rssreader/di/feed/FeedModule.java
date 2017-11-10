package tek.gezacsorba.rssreader.di.feed;

import dagger.Module;
import dagger.Provides;
import tek.gezacsorba.rssreader.di.NetworkModule;
import tek.gezacsorba.rssreader.feed.use_case.AddSourceUseCase;
import tek.gezacsorba.rssreader.feed.items.FeedCollection;
import tek.gezacsorba.rssreader.feed.FeedContract;
import tek.gezacsorba.rssreader.feed.FeedPresenter;
import tek.gezacsorba.rssreader.feed.use_case.RefreshFeedUseCase;
import tek.gezacsorba.rssreader.feed.use_case.RemoveSourceUseCase;

/**
 * Created by geza on 11/5/17.
 */
@Module(includes = NetworkModule.class)
public class FeedModule {

    FeedContract.View view;

    public FeedModule(FeedContract.View view) {
        this.view = view;
    }

    @Provides
    FeedContract.Presenter providePresenter(FeedCollection collection, RefreshFeedUseCase refreshFeedUseCase, AddSourceUseCase addSourceUseCase, RemoveSourceUseCase removeSourceUseCase) {
        return new FeedPresenter(view, collection, refreshFeedUseCase, addSourceUseCase, removeSourceUseCase);
    }

}
