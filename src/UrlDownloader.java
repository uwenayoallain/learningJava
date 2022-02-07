import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

public class UrlDownloader {
    public static void downloadFiles(String[] allLinks) throws IOException {
        for (String link : allLinks) {
            if (link != null) {
        URL downloadLink = new URL(link);
        String slug = link.replace("http://rca.ac.rw/", "");
        ReadableByteChannel rbc = Channels.newChannel(downloadLink.openStream());
        FileOutputStream fos = new FileOutputStream("src/downloads/"+slug);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
            }
        }

    public static void main(String[] args) {
        String[] allLinks = new String[100];
        try {
            Document doc = Jsoup.connect("http://rca.ac.rw").get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                if(!link.attr("href").contains("http") && !link.attr("href").contains("#") && !link.attr("href").contains("javascript") && !link.attr("href").contains("mailto") && !link.attr("href").contains("tel") && !link.attr("href").contains("#") && !link.attr("href").contains("/") && !link.attr("href").contains("?") && !link.attr("href").contains("./")){
                    if(!(Arrays.asList(allLinks).contains("http://rca.ac.rw/" + link.attr("href")))){
                    allLinks[links.indexOf(link)] = "http://rca.ac.rw/" + link.attr("href");
                    }
                }
            }

            for (String allLink : allLinks) {
                if (allLink != null) {
                    System.out.println(allLink);
                }
            }
            downloadFiles(allLinks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
