package lab3.tools;

import lab3.util.PostTokenizer;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

import static java.lang.StrictMath.log;

/**
 * Created by Ariel on 23-Jul-16.
 */
public class Classification {

    // returns Semantic Orientation from a review
    public double SemanticOrientation (String review) {
        /**
         * Algorithm based in the PMI-IR Algorithm as specified by Turney(2002) in http://www.aclweb.org/anthology/P02-1053.pdf
         */

        ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
        Analyzer cogroo = factory.createPipe();
        Document document = new DocumentImpl();
        List<Token> tokens = new ArrayList<Token>();
        List<String> phrases = new ArrayList<String>();
        double SO = 0;


        document.setText(review);
        cogroo.analyze(document);

        // get sentence from review
        for (Sentence sentence : document.getSentences()) {
            System.out.println(sentence.getText());
            // get tokens from sentence
            for (Token token : sentence.getTokens()) {
                // removes names and non-text-characters
                if (token.getPOSTag().contentEquals("prop") || !PostTokenizer.isValidToken(token)) {
                    continue;
                }
                tokens.add(token);
            }
        }

        tokens.forEach((token) -> {
            if(token.getPOSTag().contentEquals("adj") || token.getLexeme().contentEquals("adv")) {
                phrases.add(token.getLexeme());
            }
        });

        for (String phrase : phrases) {
            try {
                SO += log((SearchAgent.getGoogleHits(phrase.toString() + " AND ótimo")) * SearchAgent.getGoogleHits("ruim") /
                         (SearchAgent.getGoogleHits(phrase.toString() + " AND ruim")) * SearchAgent.getGoogleHits("ótimo"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("SO: " + SO + "\n");
        return SO;

    }

}
