package lab3.tools;

import lab1.util.FileManager;
import lab3.util.PostTokenizer;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

import static java.lang.StrictMath.log;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ariel on 23-Jul-16.
 */
public class Classification {

    StringBuilder positives = new StringBuilder(), negatives = new StringBuilder();

    // returns Semantic Orientation from a review
    public void SemanticOrientation (String review) {
        /**
         * Algorithm based in the PMI-IR Algorithm as specified by Turney(2002) in http://www.aclweb.org/anthology/P02-1053.pdf
         */

        ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
        Analyzer cogroo = factory.createPipe();
        Document document = new DocumentImpl();

        List<String> phrases = new ArrayList<String>();
        List<Token> tokens = new ArrayList<Token>();

        document.setText(review);
        cogroo.analyze(document);

        // get sentence from review
        for (Sentence sentence : document.getSentences()) {
            // get tokens from sentence
            for (Token token : sentence.getTokens()) {
                token.setLexeme(token.getLexeme().replace("'", "").replace(".", "").replace("<br>", "").replace(",", ""));
                tokens.add(token);
            }
        }

        tokens.forEach((token) -> {
            if(token.getPOSTag().contentEquals("adj") || token.getLexeme().contentEquals("adv")) {
                phrases.add(token.getLexeme());
            }
            if (!phrases.isEmpty() && token.getPOSTag().contentEquals("n") && !phrases.get(0).contains(" ")) {
                String p = phrases.remove(0) +" "+ token.getLexeme();
                phrases.add(p);
            }
        });

        double SO = 0;
        // Generate SO for each phrase.
        for (String phrase : phrases) {
            try {
                SO += log((SearchAgent.getHits("bing",phrase.toString()+ " \"muito bom\"" +" " +
                        "-site:filmow.com -site:adorocinema.com.br") +0.01)/(
                        SearchAgent.getHits("bing",phrase.toString()+" \"ruim\"" +
                                " -site:filmow.com -site:adorocinema.com.br")+0.01));
            } catch (IOException e) {
                e.printStackTrace();
            }
         //   System.out.println("Phrase: "+ phrase.toString() +" - Cumulative SO: " + SO);
        }

        if (SO > 0) {
            System.out.println("Positive: " + review.toString());
            positives.append(review.toString() + "\n");
        }
        else if (SO < 0) {
            System.out.println("Negative: " + review.toString());
            negatives.append(review.toString() + "\n");
        }
    }

    public void generateCorpora() throws IOException {
        (new FileManager()).writeToFile("corpus/corpus_positives_reviews.txt", positives.toString());
        (new FileManager()).writeToFile("corpus/corpus_negatives_reviews.txt", negatives.toString());

        System.out.println("Reviews classificadas e corpora gerados.\n");
    }
}