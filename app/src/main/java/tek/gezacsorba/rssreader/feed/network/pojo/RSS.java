package tek.gezacsorba.rssreader.feed.network.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class RSS {

    @Attribute
    String version;

    @Element
    Channel channel;


    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "RSS {" +
                "version='" + version + '\'' +
                ",\n channel=" + channel + "\n" +
                '}';
    }

    public String getVersion() {
        return version;
    }
}
