package tek.gezacsorba.rssreader.feed.details;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by geza on 11/8/17.
 */

public class FeedDetailsPresenter {

    public void popShareWindow(AppCompatActivity activity, String link){
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(link))
                .build();
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }
}
