package tek.gezacsorba.rssreader;

import android.support.annotation.NonNull;

import junit.framework.Assert;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import tek.gezacsorba.rssreader.feed.items.FeedCollection;
import tek.gezacsorba.rssreader.feed.items.FeedItem;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {



    }

    @NonNull
    private FeedItem getFeedItem(final String dummy) {
        return new FeedItem(){

            @Override
            public String getTitle() {
                return dummy;
            }

            @Override
            public String getDescription() {
                return dummy;
            }

            @Override
            public String getLink() {
                return dummy;
            }
        };
    }
}