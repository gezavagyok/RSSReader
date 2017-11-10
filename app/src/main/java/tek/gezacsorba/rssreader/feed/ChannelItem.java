package tek.gezacsorba.rssreader.feed;

import tek.gezacsorba.rssreader.feed.network.pojo.Channel;

/**
 * Created by geza on 11/9/17.
 */

public class ChannelItem {

    Channel channel;
    String url;

    public ChannelItem(Channel channel, String url) {
        this.channel = channel;
        this.url = url;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getUrl() {
        return url;
    }
}
