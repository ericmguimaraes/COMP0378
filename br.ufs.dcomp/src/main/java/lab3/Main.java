package lab3;

import lab3.util.SearchAgent;

import java.io.IOException;

/**
 * Created by Ariel on 22-Jul-16.
 */

public class Main {
    public static void main(String[] args) {
        SearchAgent gAgent = new SearchAgent("vem monstro", "google");
        SearchAgent fAgent = new SearchAgent("procurando-dory-t61563/", "filmow");
        SearchAgent acAgent = new SearchAgent("226078", "adorocinema");
        try {
            System.out.println("gAgent: " + gAgent.getGoogleHits() + " results" +
                    "\nfAgent: " + fAgent.getFilmowReview() + "\nacAgent: " + acAgent.getAdoroCinemaReview());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
