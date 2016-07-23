package lab3;

import lab3.util.SearchAgent;

import java.io.IOException;

/**
 * Created by Ariel on 22-Jul-16.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Google Search Agent: " + SearchAgent.getGoogleHits("vem monstro") + " results"
                + "\nFilmow Search Agent: " + SearchAgent.getReview("filmow", "procurando-dory-t61563")
                + "\nAdoro Cinema Search Agent: " + SearchAgent.getReview("adorocinema", "226078"));

        SearchAgent.generateCorpora();
    }
}
