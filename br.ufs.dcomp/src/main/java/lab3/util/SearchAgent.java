package lab3.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lab1.util.FileManager;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by Ariel on 23-Jul-16.
 */
public class SearchAgent {

    // movie lists for get reviews
    static final String[] fMovies = {"indie-game-the-movie-t40317", "a-viagem-de-chihiro-t1246", "sr-ninguem-t12536",
            "cidadaoquatro-t109116", "waking-life-t1414", "dogville-t4855", "masculino-feminino-t12255"};
    static final String[] acMovies = {"226260", "980", "125828", "210222", "39187", "123734", "27442"};

    // For google bot: If you omit the user agent, then you get a 403 back.
    // If you simulate a web browser, then you get a way much larger HTML response back.
    static final String userAgent = "Googlebot/2.1 (+http://www.google.com/bot.html)";
    static final String charset = "UTF-8";

    public static long getGoogleHits (String search) throws IOException {

        String target = "http://www.google.com/search?q=";
        Document doc = Jsoup.connect(target + URLEncoder.encode(search, charset)).userAgent(userAgent).get();
        String resultStats = doc.select("div#resultStats").text();

        resultStats = resultStats.replace("Aproximadamente ","")
                                 .replace(" ","")
                                 .replace("resultados", "")
                                 .replace(".", "");

        Long hits = Long.parseUnsignedLong(resultStats);

        return hits;
    }

    public static String getReview (String target, String movie) throws IOException {

        String review = null;
        Document doc;
        Element ACreview;

        switch (target) {
            case ("filmow"):
                target = "http://www.filmow.com/";
                if (movie == "") movie = fMovies[(new Random()).nextInt(fMovies.length)];
                doc = Jsoup.connect(target + movie).get();
                Element FilmowReview = doc.select("#all-comments p").get((new Random()).nextInt(10));

                review = FilmowReview.toString();
                break;
            case ("adorocinema"):
                target = "http://www.adorocinema.com/filmes/filme-";
                if (movie == "") movie = acMovies[(new Random()).nextInt(fMovies.length)];
                doc = Jsoup.connect(target + movie + "/criticas/espectadores/").get();
                ACreview = doc.select("#content-start > article > section:nth-child(3) > div.reviews-users-comment " +
                        "> div:nth-child("+(new Random()).nextInt(10)+") > div.col-xs-12.col-sm-9 > p").first();

                review = ACreview.toString()
                        .replace("<span class=\"blue-link user_url\" data-ac=\"ACrACr\" target=\"_blank\"> </span>" , "")
                        .replace(" itemprop=\"description\"","");
                break;
            default:
                System.out.println("What if the target?");
                break;
        }

        return review;
    }

    public static void generateCorpora () throws IOException {
        List<String> reviews = new ArrayList<String>();
        Document fDoc, acDoc;
        String fURL = "http://www.filmow.com/", acURL = "http://www.adorocinema.com/filmes/filme-";
        int length = fMovies.length;
        Element Freview, ACreview;
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            fDoc = Jsoup.connect(fURL + fMovies[random.nextInt(length)]).get();
            Freview = fDoc.select("#all-comments p").get(random.nextInt(10));

            if (Freview == null)
                continue;
            reviews.add(Freview.toString());
        }

        for (int i = 1; i < 20; i++) {
            acDoc = Jsoup.connect(acURL + acMovies[random.nextInt(length)] + "/criticas/espectadores/").get();
            ACreview = acDoc.select("#content-start > article > section:nth-child(3) > div.reviews-users-comment " +
                    "> div:nth-child("+random.nextInt(10)+") > div.col-xs-12.col-sm-9 > p").first();

            if (ACreview == null)
                continue;
            reviews.add(ACreview.toString()
                    .replace("<span class=\"blue-link user_url\" data-ac=\"ACrACr\" target=\"_blank\"> </span>" , "")
                    .replace(" itemprop=\"description\"",""));
        }

        FileManager outFile = new FileManager();
        outFile.writeToFile("corpora.txt", reviews);
    }


}
