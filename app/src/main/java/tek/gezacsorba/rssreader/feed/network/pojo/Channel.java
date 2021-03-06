package tek.gezacsorba.rssreader.feed.network.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

import tek.gezacsorba.rssreader.feed.items.FeedItem;

@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2005/Atom", prefix = "atom")
})
@Root(strict = false)
public class Channel {
    // Tricky part in Simple XML because the link is named twice
    @ElementList(entry = "link", inline = true, required = false)
    public List<Link> links;

    @ElementList(name = "item", required = true, inline = true)
    public List<Item> itemList;


    @Element
    public String title;
    @Element
    public String language;

    @Element(name = "ttl", required = false)
    public int ttl;

    @Element(name = "pubDate", required = false)
    public String pubDate;

    @Override
    public String toString() {
        return "Channel{\n" +
                "links=" + links + "\n" +
                ", itemList=" + itemList + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", language='" + language + '\'' + "\n" +
                ", ttl=" + ttl +
                ", pubDate='" + pubDate + '\'' + "\n" +
                '}';
    }

    public static class Link {
        @Attribute(required = false)
        public String href;

        @Attribute(required = false)
        public String rel;

        @Attribute(name = "type", required = false)
        public String contentType;

        @Text(required = false)
        public String link;
    }

    public static class Enclosure {
        @Attribute(required = false)
        String url;
        @Attribute(required = false)
        String length;
        @Attribute(required = false)
        String type;
    }

    @Root(name = "item", strict = false)
    public static class Item implements FeedItem {

        @Element(name = "title", required = true)
        String title;//The title of the item.	Venice Film Festival Tries to Quit Sinking
        @Element(name = "link", required = true)
        String link;//The URL of the item.	http://www.nytimes.com/2002/09/07/movies/07FEST.html
        @Element(name = "description", required = true)
        String description;//The item synopsis.	Some of the most heated chatter at the Venice Film Festival this week was about the way that the arrival of the stars at the Palazzo del Cinema was being staged.
        @Element(name = "author", required = false)
        String author;//Email address of the author of the item. More.	oprah@oxygen.net
        @Element(name = "category", required = false)
        String category;//Includes the item in one or more categories. More.	Simpsons Characters
        @Element(name = "comments", required = false)
        String comments;//URL of a page for comments relating to the item. More.	http://www.myblog.org/cgi-local/mt/mt-comments.cgi?entry_id=290
        @Element(name = "enclosure", required = false)
        Enclosure enclosure;//	Describes a media object that is attached to the item. More.	<enclosure url="http://live.curry.com/mp3/celebritySCms.mp3" length="1069871" type="audio/mpeg"/>
        @Element(name = "guid", required = false)
        String guid;//A string that uniquely identifies the item. More.	<guid isPermaLink="true">http://inessential.com/2002/09/01.php#a2</guid>
        @Element(name = "pubDate", required = false)
        String pubDate;//	Indicates when the item was published. More.	Sun, 19 May 2002 15:21:36 GMT
        @Element(name = "source", required = false)
        String source;//	The RSS channel that the item came from. More.

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getDescription() {
            return description;
        }

        public String getAuthor() {
            return author;
        }

        public String getCategory() {
            return category;
        }

        public String getComments() {
            return comments;
        }

        public Enclosure getEnclosure() {
            return enclosure;
        }

        public String getImage() {
            return getEnclosure().url;
        }

        public String getGuid() {
            return guid;
        }

        public String getPubDate() {
            return pubDate;
        }

        public String getSource() {
            return source;
        }

        @Override
        public String toString() {
            return "Item { \n" +
                    "title='" + title + '\'' +
                    ", \nlink='" + link + '\'' +
                    ", \ndescription='" + description + '\'' +
                    ", \nauthor='" + author + '\'' +
                    ", \ncategory='" + category + '\'' +
                    ", \ncomments='" + comments + '\'' +
                    ", \nenclosure='" + enclosure + '\'' +
                    ", \nguid='" + guid + '\'' +
                    ", \npubDate='" + pubDate + '\'' +
                    ", \nsource='" + source + '\'' +
                    "\n}";
        }
    }

    public List<Link> getLinks() {
        return links;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public int getTtl() {
        return ttl;
    }

    public String getPubDate() {
        return pubDate;
    }
}

