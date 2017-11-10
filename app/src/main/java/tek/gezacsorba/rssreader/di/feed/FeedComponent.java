package tek.gezacsorba.rssreader.di.feed;

import dagger.Component;
import tek.gezacsorba.rssreader.FeedActivity;

/**
 * Created by geza on 11/5/17.
 */
@Component(modules = FeedModule.class)
public interface FeedComponent {
    void inject(FeedActivity activity);
}
