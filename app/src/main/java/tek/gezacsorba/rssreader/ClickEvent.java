package tek.gezacsorba.rssreader;

/**
 * Created by geza on 11/9/17.
 */

public interface ClickEvent {

    int TYPE_DELETE = 0x0;
    int TYPE_VIEW = 0x1;
    int TYPE_ADD_LINK = 0x2;

    int getType();
}
