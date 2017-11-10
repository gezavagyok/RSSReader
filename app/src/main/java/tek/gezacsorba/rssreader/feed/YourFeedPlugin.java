package tek.gezacsorba.rssreader.feed;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import tek.gezacsorba.rssreader.ClickEvent;
import tek.gezacsorba.rssreader.R;

/**
 * Created by geza on 11/9/17.
 */

public class YourFeedPlugin implements MenuPlugin {

    View root;

    @BindView(R.id.channels_view)
    RecyclerView feedList;

    @OnClick(R.id.btn_show_input_views)
    void onShowClicked(View v) {
        View inputView = LayoutInflater.from(v.getContext()).inflate(R.layout.add_source_window,null, false);
        final EditText rssAddress = inputView.findViewById(R.id.et_rss_address);
        new AlertDialog.Builder(v.getContext()).setView(inputView).setPositiveButton("Add", (dialog, which) -> adapter.getSubject().onNext(new AddEvent(rssAddress.getText().toString()))).show();
    }

    YourFeedAdapter adapter;

    @Override
    public void init() {
        adapter = new YourFeedAdapter(new ArrayList<>());

        feedList.setLayoutManager(new LinearLayoutManager(feedList.getContext()));
        feedList.setAdapter(adapter);
    }

    public void addChannel(ChannelItem channel) {
        adapter.add(channel);
    }

    public void removeChannel(ChannelItem channel) {
        adapter.remove(channel);
    }

    public Observable<ClickEvent> getClicks() {
        return adapter.getEvents();
    }

    @Override
    public void setRootView(View root) {
        this.root = root;
        ButterKnife.bind(this, root);
    }

    public static class YourFeedAdapter extends RecyclerView.Adapter<YourFeedViewHolder> {

        List<ChannelItem> items;
        Subject<ClickEvent> clicks;

        public YourFeedAdapter(List<ChannelItem> items) {
            this.items = items;
            this.clicks = PublishSubject.create();
        }

        public void add(ChannelItem item) {
            items.add(0, item);
            notifyDataSetChanged();
        }

        public void remove(ChannelItem item) {
            int position = 0;
            for (int i = 0; i < items.size(); i++) {
                if (item.equals(items.get(i))) {
                    position = i;
                }
            }
            items.remove(item);
            notifyItemRemoved(position);
        }

        public Observable<ClickEvent> getEvents() {
            return clicks;
        }

        Subject<ClickEvent> getSubject() {
            return clicks;
        }

        @Override
        public YourFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new YourFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_feed, parent, false));
        }

        @Override
        public void onBindViewHolder(YourFeedViewHolder holder, int position) {
            final ChannelItem channel = items.get(position);
            holder.channelName.setText(channel.getChannel().getTitle());
            holder.channelDelete.setOnClickListener(v -> clicks.onNext(new DeleteEvent(channel)));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public static class YourFeedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_channel_name)
        TextView channelName;

        @BindView(R.id.iv_channel_delete)
        ImageView channelDelete;

        public YourFeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
