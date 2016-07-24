package lab3;

import lab1.util.FileManager;
import lab3.tools.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ariel on 22-Jul-16.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Google Search Agent: " + SearchAgent.getGoogleHits("vem monstro") + " results"
                + "\nFilmow Search Agent: " + SearchAgent.getReview("filmow", "procurando-dory-t61563")
                + "\nAdoro Cinema Search Agent: " + SearchAgent.getReview("adorocinema", "226078"));

        Classification classifier = new Classification();

        for (String review: (new FileManager()).readFile("corpora.txt")) {
            classifier.SemanticOrientation(review);
        }

    }
}
