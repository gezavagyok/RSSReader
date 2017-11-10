package tek.gezacsorba.rssreader.feed.items;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by geza on 11/7/17.
 */

public class FeedCollection extends ArrayList<FeedItem> {

    int currentIndex;

    @Inject
    public FeedCollection() {

    }

    public FeedItem getCurrent() {
        return get(currentIndex);
    }

    public FeedItem getNext() {
        if(currentIndex + 1 >= size()) {
            return get(currentIndex);
        }

        return get(++currentIndex);
    }

    public FeedItem getPrevious() {
        if(currentIndex - 1 < 0) {
            return get(currentIndex);
        }

        return get(--currentIndex);
    }
}
