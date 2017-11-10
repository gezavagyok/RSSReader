package tek.gezacsorba.rssreader.di.feed;

import dagger.Component;
import tek.gezacsorba.rssreader.feed.details.FeedDetailsActivity;

/**
 * Created by geza on 11/8/17.
 */
@Component(modules = FeedDetailsModule.class)
public interface FeedDetailsComponent {
    void inject(FeedDetailsActivity activity);
}
