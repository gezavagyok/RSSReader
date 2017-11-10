package tek.gezacsorba.rssreader;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tek.gezacsorba.rssreader.architecture.BaseActivity;
import tek.gezacsorba.rssreader.feed.network.RetrofitFactory;
import tek.gezacsorba.rssreader.feed.network.RssService;
import tek.gezacsorba.rssreader.feed.network.pojo.Channel;

public class MainActivity extends BaseActivity {

    @Override
    protected void injectDependencies() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        RssService service = RetrofitFactory.createXmlAdapterFor(RssService.class,"https://www.nasa.gov/");
        service.getFeed("https://feeds.bbci.co.uk/news/rss.xml")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rss -> {
                    //TextView tv = findViewById(R.id.debug);
                    List<Channel.Item> items = rss.getChannel().getItemList();
                    StringBuilder builder = new StringBuilder();
                    for (Channel.Item item : items) {
                        builder.append("Title: ").append(item.getTitle()).append("\n");
                        builder.append("Link: ").append(item.getLink()).append("\n");
                        builder.append("---\n");
                    }
                    //tv.setText(builder.toString());
                }, this::showError);
    }

}
