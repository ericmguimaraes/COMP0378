package lab3.util;

import org.cogroo.text.Token;

/**
 * Created by Ariel on 23-Jul-16.
 */

// precisamos tornar isso mais conciso e elegante
public class PostTokenizer {

    // remove irrelevant tokens
    public static boolean isValidToken(Token token) {
        if (token.getPOSTag().contentEquals(".") || token.getLexeme().contentEquals("(") ||
            token.getPOSTag().contentEquals(")") || token.getPOSTag().contentEquals("?") ||
            token.getPOSTag().contentEquals("“") || token.getPOSTag().contentEquals("”") ||
            token.getPOSTag().contentEquals(",") || token.getPOSTag().contentEquals(";") ||
            token.getLexeme().contentEquals("<p>") || token.getLexeme().contentEquals("</p>")||
            token.getLexeme().contentEquals("\uFEFF") || token.getLexeme().contentEquals("<br>")) {
            return false;
        } else {
            return true;
        }
    }
}
