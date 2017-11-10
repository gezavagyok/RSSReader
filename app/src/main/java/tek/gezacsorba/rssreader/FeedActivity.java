package tek.gezacsorba.rssreader;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tek.gezacsorba.rssreader.architecture.BaseActivity;
import tek.gezacsorba.rssreader.di.NetworkModule;
import tek.gezacsorba.rssreader.di.feed.DaggerFeedComponent;
import tek.gezacsorba.rssreader.di.feed.FeedModule;
import tek.gezacsorba.rssreader.feed.AddEvent;
import tek.gezacsorba.rssreader.feed.ChannelItem;
import tek.gezacsorba.rssreader.feed.DeleteEvent;
import tek.gezacsorba.rssreader.feed.FeedContract;
import tek.gezacsorba.rssreader.feed.MenuPlugin;
import tek.gezacsorba.rssreader.feed.UserInfoPlugin;
import tek.gezacsorba.rssreader.feed.YourFeedPlugin;
import tek.gezacsorba.rssreader.feed.details.FeedDetailsActivity;
import tek.gezacsorba.rssreader.feed.items.FeedItem;

public class FeedActivity extends BaseActivity
        implements FeedContract.View {

    @Inject
    FeedContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.menu_plugin_container)
    LinearLayout pluginContainer;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.iv_image)
    ImageView imageView;

    @BindView(R.id.tv_title)
    TextView titleView;

    @BindView(R.id.tv_description)
    TextView descriptionView;

    YourFeedPlugin plugin2;

    OnSwipeTouchListener swipeTouchListener;

    @Override
    protected void injectDependencies() {
        DaggerFeedComponent.builder()
                .feedModule(new FeedModule(this))
                .networkModule(new NetworkModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void initView() {
        presenter.onCreate();
    }

    @Override
    public void showFeed(FeedItem item) {
        Snackbar.make(getWindow().getDecorView(), item.getTitle(), Snackbar.LENGTH_SHORT).show();
        imageView.setOnTouchListener(swipeTouchListener);
        showLoading();
        imageView.setOnClickListener(v -> startActivity(FeedDetailsActivity.getIntent(FeedActivity.this, item)));
        Glide.with(this)
                .load(item.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        hideLoading();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        hideLoading();
                        return false;
                    }
                }).into(imageView);
        titleView.setText(item.getTitle());
        descriptionView.setText(item.getDescription());
    }

    @Override
    public void addChannelToMenu(ChannelItem channel) {
        plugin2.addChannel(channel);
    }

    @Override
    public void removeMenuItem(ChannelItem item) {
        plugin2.removeChannel(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        swipeTouchListener = new OnSwipeTouchListener(this,presenter);

        MenuPlugin plugin = new UserInfoPlugin();
        plugin.setRootView(findViewById(R.id.header_root));
        plugin.init();
        plugin2 = new YourFeedPlugin();
        plugin2.setRootView(findViewById(R.id.your_feed_root));
        plugin2.init();
        plugin2.getClicks()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clickEvent -> {
                    switch (clickEvent.getType()) {
                        case ClickEvent.TYPE_DELETE:
                            presenter.removeSource(((DeleteEvent) clickEvent).getChannel());
                            break;
                        case ClickEvent.TYPE_ADD_LINK:
                            presenter.addSource(((AddEvent) clickEvent).getLink());
                            break;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_paste_url) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.hasPrimaryClip()) {
                // handle the first item.
                ClipData.Item i = clipboard.getPrimaryClip().getItemAt(0);
                String pasteData = i.getText().toString();
                if (pasteData.trim().endsWith("xml")) {
                    presenter.addSource(pasteData);
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
