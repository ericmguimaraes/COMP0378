package lab3.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lab1.util.FileManager;
import org.jsoup.select.Elements;

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
    static final String[] fMovies = {"indie-game-the-movie-t40317", "a-viagem-de-chihiro-t1246", "cidadaoquatro-t109116", "waking-life-t1414", "dogville-t4855", "interestelar-t27814",
            "sr-ninguem-t12536", "masculino-feminino-t12255", "edukators-os-educadores-t4890", "birdman-ou-a-inesperada-virtude-da-ignorancia-t70545", "o-escafandro-e-a-borboleta-t1887",
            "cidade-de-deus-t4649", "scott-pilgrim-contra-o-mundo-t12165", "desconstruindo-harry-t2491", "efeito-borboleta-t4891", "clube-da-luta-t318", "ela-t52084"};
    static final String[] acMovies = {"226260", "980", "125828", "210222", "39187", "123734", "27442", "10568", "146349", "448", "196960", "18598", "225953", "10126",
            "14264", "19776", "56833"};

    public static long getHits (String target, String search) throws IOException {
        Long hits = Long.valueOf(0);
        Document doc;
        String userAgent, charset = "UTF-8";

        switch (target) {
            case ("google"):
                target = "http://www.google.com/search?q=";
                userAgent = "Googlebot/2.1 (+http://www.google.com/bot.html)";
                doc = Jsoup.connect(target + URLEncoder.encode(search, charset)).userAgent(userAgent).get();
                String results = doc.select("div#resultStats").text();

                results = results.replace("Aproximadamente ", "")
                        .replace(" ", "")
                        .replace("resultados", "")
                        .replace("resultado", "")
                        .replace(".", "");
                hits = Long.parseUnsignedLong(results);
                break;
            case ("bing"):
                target = "http://www.bing.com/search?q=";
                userAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.15 (KHTML, like Gecko) Chrome/24.0.1295.0 Safari/537.15";
                doc = Jsoup.connect(target + URLEncoder.encode(search, charset)).userAgent(userAgent).get();
                results = doc.select("#b_tween > span.sb_count").text();

                results = results.replace(".", "")
                                 .replace("resultados", "")
                                 .replace(" ", "");
                if (results.isEmpty())
                    break;
                hits = Long.parseUnsignedLong(results);
                break;
        }

        return hits;
    }

    public static String getReview (String target, String movie) throws IOException {

        String review = null;
        Document doc;
        Element ACreview;

        switch (target) {
            case ("filmow"):
                System.out.println("******Capturando review aleatória do Filmow******");

                target = "http://www.filmow.com/";
                if (movie == "") movie = fMovies[(new Random()).nextInt(fMovies.length)];
                doc = Jsoup.connect(target + movie).get();
                Element FilmowReview = doc.select("#all-comments p").get((new Random()).nextInt(10));

                review = FilmowReview.toString().replace("<p>", "")
                                                .replace("</p>", "") + "\n";
                break;
            case ("adorocinema"):
                System.out.println("******Capturando review aleatória do Adoro Cinema******");
                target = "http://www.adorocinema.com/filmes/filme-";
                if (movie == "") movie = acMovies[(new Random()).nextInt(acMovies.length)];
                doc = Jsoup.connect(target + movie + "/criticas/espectadores/").get();
                ACreview = doc.select("#content-start > article > section:nth-child(3) > div.reviews-users-comment " +
                        "> div:nth-child("+(new Random()).nextInt(5)+") > div.col-xs-12.col-sm-9 > p").first();

                review = ACreview.toString().replace("<p>","")
                                            .replace("</p>","")
                                            .replace("<span class=\"blue-link user_url\" data-ac=\"ACrACr\" target=\"_blank\"> </span>" , "")
                                            .replace(" itemprop=\"description\"","") + "\n";
                break;
            default:
                System.out.println("Where is the target?");
                break;
        }

        return review;
    }

    public static void generateCorpus () throws IOException {
        StringBuilder corpus = new StringBuilder();
        Document fDoc, acDoc;
        String fURL = "http://www.filmow.com/", acURL = "http://www.adorocinema.com/filmes/filme-";
        int length = fMovies.length;
        Elements ACreviews = new Elements(), Freviews = new Elements();
        Random random = new Random();
        System.out.println("******Gerando Corpus: Reviews******");

        System.out.println("1. Capturando reviews do Filmow");

        for (int i = 0; i < length; i++) {
            fDoc = Jsoup.connect(fURL + fMovies[i]).get();
            Elements reviews = fDoc.select("#all-comments p");


            if (reviews == null)
                continue;
            Freviews.addAll(reviews);
        }

        corpus.append(Freviews.toString().replace("<p>", "")
                .replace("</p>", "\n")
                .replace("<br>", ""));

        System.out.println("2. Capturando reviews do Adoro Cinema");

        for (int i = 1; i < length; i++) {
            acDoc = Jsoup.connect(acURL + acMovies[i] + "/criticas/espectadores/").get();
            Element reviews = acDoc.select("#content-start > article > section:nth-child(3) > div.reviews-users-comment " +
                    "> div:nth-child(1) > div.col-xs-12.col-sm-9 > p").first();

            if (reviews == null)
                continue;
            ACreviews.add(reviews);
        }

        corpus.append(ACreviews.toString().replace("<p> ","") .replace("</p>","\n")
                .replace("<span class=\"blue-link user_url\" data-ac=\"ACrACr\" target=\"_blank\"> </span>" , "")
                .replace(" itemprop=\"description\"","")
                .replace ("<span class=\"ACrACr blue-link user_url\" target=\"_blank\"> </span>", ""));

        (new FileManager()).writeToFile("corpus/corpus_reviews.txt", corpus.toString());
        System.out.println("Corpus gerado.\n");
    }

}
