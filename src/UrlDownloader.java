import me.tongfei.progressbar.ProgressBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.Scanner;


public class UrlDownloader {
    public static void downloadFiles(String[] allLinks,String Baseurl, int totalLinks) throws IOException {
        String directory = "src/downloads/"+Baseurl.replace("http://","").replace("https://","");
        System.out.println("Downloading files to " + directory);
        int downloaded = 0;
        boolean mkdirs = new File(directory).mkdirs();
        if (mkdirs) {
            System.out.println("Directory created");
        }
        try (ProgressBar pb = new ProgressBar("Downloading", 100)) {
        for (String link : allLinks) {
            if (link != null && link.contains(Baseurl) && !link.contains("/*/")) {
        URL downloadLink = new URL(link);
        String slug = link.replace(Baseurl, "");
        ReadableByteChannel rbc = Channels.newChannel(downloadLink.openStream());
        FileOutputStream fos = new FileOutputStream(directory+slug.replace("/","_"));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        downloaded++;
        int done = downloaded*100/totalLinks;
//        pb.setExtraMessage(link + "is Done" +"["+done+"/"+100+"]\n");
        pb.stepTo(done);
        pb.maxHint(100);
        pb.refresh();
                }
            }
        }
        System.out.println("Download Complete," + downloaded + " files");
    }

    public static void main(String[] args) {
        String[] allLinks = new String[1000];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the url :");
        String EnteredUrl = scanner.nextLine();
        try {
            Document doc = Jsoup.connect(EnteredUrl).get();
            Elements links = doc.select("a[href]");
            int foundLinks = 0;
            int downloadableLinks = 0;
            for (Element link : links) {
                if( !link.attr("href").contains("#") && !link.attr("href").contains("mailto") && !link.attr("href").contains("tel")  && !link.attr("href").contains("?") && !link.attr("href").contains("./")){
                    if(!(Arrays.asList(allLinks).contains(EnteredUrl + link.attr("href")))){
                        if(!link.attr("href").contains("http") && !link.attr("href").contains("https")){
                    allLinks[links.indexOf(link)] = EnteredUrl + link.attr("href");
                    }else{
                            allLinks[links.indexOf(link)] =link.attr("href");
                        }
                }
            }
            }
            System.out.println("Found " + foundLinks + " links");
            System.out.println("Displaying links[all links]");
            for (String allLink : allLinks) {
                if (allLink != null) {
                    System.out.println(allLink);
                    if(allLink.contains(EnteredUrl) && !allLink.contains("/*/")){
                        downloadableLinks++;
                    }
                    foundLinks++;
                }
            }
            System.out.println("Total Links Found: " + foundLinks);
            System.out.println("Downloading files");
            downloadFiles(allLinks, EnteredUrl,downloadableLinks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
