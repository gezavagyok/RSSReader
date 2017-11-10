package tek.gezacsorba.rssreader.feed.details;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import javax.inject.Inject;

import butterknife.BindView;
import tek.gezacsorba.rssreader.R;
import tek.gezacsorba.rssreader.architecture.BaseActivity;
import tek.gezacsorba.rssreader.di.feed.DaggerFeedDetailsComponent;
import tek.gezacsorba.rssreader.feed.items.FeedItem;

/**
 * Created by geza on 11/7/17.
 */

public class FeedDetailsActivity extends BaseActivity{

    private static final String LINK_PARAM = "link_param";
    private static final String TITLE_PARAM = "title_param";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.webview)
    WebView webView;

    @Inject
    FeedDetailsPresenter presenter;

    @Override
    protected void injectDependencies() {
        DaggerFeedDetailsComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_details;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE_PARAM));
        webView.loadUrl(getIntent().getStringExtra(LINK_PARAM));

    }

    public static Intent getIntent(Context context, FeedItem current) {
        Intent intent = new Intent(context, FeedDetailsActivity.class);
        intent.putExtra(LINK_PARAM, current.getLink());
        intent.putExtra(TITLE_PARAM, current.getTitle());
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feed_details,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                presenter.popShareWindow(this, getIntent().getStringExtra(LINK_PARAM));
                return true;

            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
