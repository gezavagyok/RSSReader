package tek.gezacsorba.rssreader.feed;

import tek.gezacsorba.rssreader.ClickEvent;
import tek.gezacsorba.rssreader.feed.network.pojo.Channel;

/**
 * Created by geza on 11/9/17.
 */

public class DeleteEvent implements ClickEvent {

    ChannelItem channel;

    public DeleteEvent(ChannelItem channel) {
        this.channel = channel;
    }

    public ChannelItem getChannel() {
        return channel;
    }

    @Override
    public int getType() {
        return ClickEvent.TYPE_DELETE;
    }
}
