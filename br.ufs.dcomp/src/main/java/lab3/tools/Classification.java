package lab3.tools;

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

    // returns Semantic Orientation from a review
    public double SemanticOrientation (String review) {
        /**
         * Algorithm based in the PMI-IR Algorithm as specified by Turney(2002) in http://www.aclweb.org/anthology/P02-1053.pdf
         */

        ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
        Analyzer cogroo = factory.createPipe();
        Document document = new DocumentImpl();

        List<String> phrases = new ArrayList<String>();
        List<Token> tokens = new ArrayList<Token>();
        double SO = 0;

        document.setText(review);
        cogroo.analyze(document);

        System.out.println("Review: " + document.getText());

        // get sentence from review
        for (Sentence sentence : document.getSentences()) {
            // get tokens from sentence
            for (Token token : sentence.getTokens()) {
                token.setLexeme(token.getLexeme().replace("'", "").replace(".", "").replace("<br>", ""));
                tokens.add(token);
            }
        }

        tokens = PostTokenizer.refineTokens(tokens);

        tokens.forEach((token) -> {
            if(token.getPOSTag().contentEquals("adj") || token.getLexeme().contentEquals("adv")) {
                phrases.add(token.getLexeme());
            }
            if (!phrases.isEmpty() && token.getPOSTag().contentEquals("n") && !phrases.get(0).contains(" ")) {
                String p = phrases.remove(0) +" "+ token.getLexeme();
                phrases.add(p);
            }
        });

        // Generate SO for each phrase.
        for (String phrase : phrases) {
            try {
                SO += log((SearchAgent.getGoogleHits("(\"muito bom\" OR \"vale a pena\" OR \"recomendo\") ~"
                        +phrase.toString()+" -site:filmow.com -site:adorocinema.com.br"))/
                        (SearchAgent.getGoogleHits("(\"ruim\" OR \"chato\" OR \"cansativo\" OR \"não recomendo\") ~"
                        +phrase.toString()+ " -site:filmow.com -site:adorocinema.com.br"))+0.01);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Phrase: "+ phrase.toString() +" - Cumulative SO: " + SO);
        }

        return SO;
    }

}
