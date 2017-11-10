package tek.gezacsorba.rssreader.feed;

import tek.gezacsorba.rssreader.ClickEvent;

/**
 * Created by geza on 11/9/17.
 */

public class AddEvent implements ClickEvent {

    final String link;

    public AddEvent(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public int getType() {
        return ClickEvent.TYPE_ADD_LINK;
    }
}
