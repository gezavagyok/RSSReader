package tek.gezacsorba.rssreader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import tek.gezacsorba.rssreader.feed.network.pojo.Channel;

/**
 * Created by geza on 11/10/17.
 */

public class DateUtil {
    static public int compare(Channel.Item o1, Channel.Item o2) {
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            return format.parse(o1.getPubDate()).compareTo(format.parse(o2.getPubDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
