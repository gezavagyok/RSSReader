package tek.gezacsorba.rssreader.feed;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tek.gezacsorba.rssreader.feed.details.FeedDetailsActivity;
import tek.gezacsorba.rssreader.feed.items.FeedCollection;
import tek.gezacsorba.rssreader.feed.use_case.AddSourceUseCase;
import tek.gezacsorba.rssreader.feed.use_case.RefreshFeedUseCase;
import tek.gezacsorba.rssreader.feed.use_case.RemoveSourceUseCase;

/**
 * Created by geza on 11/5/17.
 */

public class FeedPresenter implements FeedContract.Presenter {

    private final FeedContract.View view;
    private final FeedCollection collection;
    private final RefreshFeedUseCase refreshFeedUseCase;
    private final AddSourceUseCase addSourceUseCase;
    private final RemoveSourceUseCase removeSourceUseCase;

    public FeedPresenter(FeedContract.View view, FeedCollection collection, RefreshFeedUseCase refreshFeedUseCase, AddSourceUseCase addSourceUseCase, RemoveSourceUseCase removeSourceUseCase) {
        this.view = view;
        this.collection = collection;
        this.refreshFeedUseCase = refreshFeedUseCase;
        this.addSourceUseCase = addSourceUseCase;
        this.removeSourceUseCase = removeSourceUseCase;
    }

    @Override
    public void onCreate() {
        view.showLoading();
        refreshFeedUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(collection::addAll, view::showError, () -> {
                    view.hideLoading();
                    view.showFeed(collection.getCurrent());
                });
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
    }

    @Override
    public void loadNextFeed() {
        view.showFeed(collection.getNext());
    }

    @Override
    public void loadPreviousFeed() {
        view.showFeed(collection.getPrevious());
    }

    @Override
    public void loadDetails(Context context) {
        context.startActivity(FeedDetailsActivity.getIntent(context, collection.getCurrent()));
    }

    @Override
    public void removeSource(final ChannelItem item) {
        view.showLoading();
        removeSourceUseCase.execute(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completed -> {
                            view.removeMenuItem(item);
                            onCreate();
                        }
                        , view::showError);
    }

    @Override
    public void addSource(String url) {
        view.showLoading();
        addSourceUseCase.execute(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channel -> {
                    view.addChannelToMenu(channel);
                    onCreate();
                }, view::showError);
    }
}
