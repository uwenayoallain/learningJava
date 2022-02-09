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
    public static void downloadFiles(String[] allLinks,String Baseurl) throws IOException {
        String directory = "src/downloads/"+Baseurl.replace("http://","").replace("https://","");
        System.out.println("Downloading files to " + directory);
        int downloaded = 0;
        boolean mkdirs = new File(directory).mkdirs();
        if (mkdirs) {
            System.out.println("Directory created");
        }
        for (String link : allLinks) {
            if (link != null && link.contains(Baseurl) && !link.contains("/*/")) {
        URL downloadLink = new URL(link);
        String slug = link.replace(Baseurl, "");
        ReadableByteChannel rbc = Channels.newChannel(downloadLink.openStream());
        FileOutputStream fos = new FileOutputStream(directory+slug.replace("/","_"));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        downloaded++;
        System.out.println("Downloaded " + downloaded + " files");
                }
            }
        System.out.println("Download Complete," + downloaded + " files");
    }

    public static void main(String[] args) {
        String[] allLinks = new String[100];
        Scanner scanner = new Scanner(System.in);
//        "http://rca.ac.rw/"
        System.out.println("Enter the url");
        String Enteredurl = scanner.nextLine();
        try {
            Document doc = Jsoup.connect(Enteredurl).get();
            Elements links = doc.select("a[href]");
            int foundLinks = 0;
            for (Element link : links) {
                if( !link.attr("href").contains("#") && !link.attr("href").contains("mailto") && !link.attr("href").contains("tel")  && !link.attr("href").contains("?") && !link.attr("href").contains("./")){
                    if(!(Arrays.asList(allLinks).contains(Enteredurl + link.attr("href")))){
                        if(!link.attr("href").contains("http") && !link.attr("href").contains("https")){
                    allLinks[links.indexOf(link)] = Enteredurl + link.attr("href");
                    }else{
                            allLinks[links.indexOf(link)] =link.attr("href");
                        }
                }
            }
            }

            for (String allLink : allLinks) {
                if (allLink != null) {
                    System.out.println(allLink);
                    foundLinks++;
                }
            }
            System.out.println("Total Links Found: " + foundLinks);
            System.out.println("Downloading files");
            downloadFiles(allLinks, Enteredurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
