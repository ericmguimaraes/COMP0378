package lab3.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Ariel on 23-Jul-16.
 */
public class SearchAgent {

    String search, target;
    String charset = "UTF-8";
    // to help with google searchs. If you omit the user agent, then you get a 403 back. If you simulate a web browser, then you get a way much larger HTML response back.
    final String userAgent = "For academic use bot (+http://computacao.ufs.br/)";

    public SearchAgent(String search, String target) {
        this.search = search;
        if (target == "google") {
            this.target = "http://www.google.com/search?q=";
        } else if (target == "filmow") {
            this.target = "http://www.filmow.com/";
        } else if (target == "adorocinema") {
            this.target = "http://www.adorocinema.com/filmes/filme-";
        }
    }

    public long getGoogleHits() throws IOException {

        Document doc = Jsoup.connect(target + URLEncoder.encode(search, charset)).userAgent(userAgent).get();
        String resultStats = doc.select("div#resultStats").text();

        resultStats = resultStats.replace("Aproximadamente ","")
                .replace(" ","")
                .replace("resultados", "")
                .replace(".", "");

        Long hits = Long.parseUnsignedLong(resultStats);

        return hits;
    }

    public String getFilmowReview() throws IOException {

        Document doc = Jsoup.connect(target + search).get();
        Element FilmowReview = doc.select("#comment_id-6482378 > div > div.text.comment-text > p").first();

        String review = FilmowReview.toString();

        return review;

    }

    public String getAdoroCinemaReview() throws IOException {

        Document doc = Jsoup.connect(target + search + "/criticas/espectadores/").get();
        Element ACreview = doc.select("#content-start > article > section:nth-child(3) > div.reviews-users-comment > div:nth-child(1) > div.col-xs-12.col-sm-9 > p").first();

        String review = ACreview.toString();

        return review;
    }

}
