package tek.gezacsorba.rssreader.di;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import tek.gezacsorba.rssreader.feed.FeedDatasource;
import tek.gezacsorba.rssreader.feed.network.RetrofitFactory;
import tek.gezacsorba.rssreader.feed.network.RssService;

/**
 * Created by geza on 11/7/17.
 */
@Module
public class NetworkModule {

    @Provides
    RssService provideRssFeedServices(){
        // todo: create a proper factory
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.nasa.gov/") // hack!
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        return retrofit.create(RssService.class);
    }

    @Provides
    FeedDatasource provideFeedDatasource(RssService service) {
        return new FeedDatasource(service);
    }
}
