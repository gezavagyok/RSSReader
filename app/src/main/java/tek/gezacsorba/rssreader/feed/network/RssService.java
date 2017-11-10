package tek.gezacsorba.rssreader.feed.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;
import tek.gezacsorba.rssreader.feed.network.pojo.RSS;

public interface RssService {
    /**
     * Provides RSS feed data.
     * @param feedUrl RSS Feed URL. Note: This must be fully qualified URL.
     * @return RSS Feed
     */
    @GET
    Observable<RSS> getFeed(@Url String feedUrl);
}