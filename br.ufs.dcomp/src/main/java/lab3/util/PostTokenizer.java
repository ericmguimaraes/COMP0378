package lab3.util;

import org.cogroo.text.Token;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Ariel on 23-Jul-16.
 */

public class PostTokenizer {

    // token object
    public static class oToken implements Comparable<oToken> {
        long counter;
        Token token;

        oToken (org.cogroo.text.Token token) { this.token = token; this.counter = 1;}

        @Override
        public String toString() {return "["+token.getLexeme()+": "+counter+"]";}

        @Override
        public int compareTo(oToken t2) {
            if (counter == t2.counter)
                return 0;
            if (counter > t2.counter)
                return 10;
            else
                return -10;
        }
    }

    static Pattern falses = Pattern.compile("(\\.|\\(|\"|'|\\)|,|<br>|</p>|<p>|“|\\?|;|”|\\uFEFF|\\d|\\*)");

    // remove irrelevant tokens
    static public boolean isValidToken(Token token) {
        if (token.getLexeme().matches(falses.toString()) || token.getLexeme().contentEquals("")) {
            return false;
        } else {
            token.setLexeme(token.getLexeme().toLowerCase().replaceAll("\\("," ")
                                                            .replaceAll("\\)"," ")
                                                            .replaceAll("\\d+", " ")
                                                            .replaceAll("!"," ")
                                                            .replaceAll("\\."," ")
                                                            .replaceAll(","," ")
                                                            .replaceAll("\\?"," ")
                                                            .replaceAll(":"," ")
                                                            .replaceAll(";"," ")
                                                            .replaceAll("\""," ")
                                                            .replaceAll("=", " ")
                                                            .replaceAll("\\+", " ")
                                                            .replaceAll("\\s-", " ")
                                                            .replaceAll("-\\s", " ")
                                                            .replaceAll("\\S*[\\\\/]\\S*", " ")
                                                            .replaceAll("\\s'"," ")
                                                            .replaceAll("'\\s"," ")
                                                            .replaceAll("\\s[^A-Za-záàâãéêíóôõúçÁÀÂÃÉÍÓÔÕÚÇ]+", " ")
                                                            .replaceAll("[^A-Za-záàâãéêíóôõúçÁÀÂÃÉÍÓÔÕÚÇ]+\\s", " ")
                                                            .replaceAll("\\s\\S{0,2}\\s", " ")
                                                            .replaceAll("\\S{23,}", " "));
            return true;
        }
    }
    static public List<Token> refineTokens(List<Token> inList) {
        List<oToken> oTokens = new ArrayList<>();
        List<Token> outList = new ArrayList<>();

        inList.forEach(token -> {
            if (isValidToken(token) && !token.getPOSTag().contentEquals("prop")) {

                if (oTokens.stream().filter(Token -> token.getLexeme().equals(Token.token.getLexeme())).count() == 0) {
                    oTokens.add(new oToken(token));
                } else {
                    for(oToken t : oTokens){
                        if(t.token.getLexeme().contentEquals(token.getLexeme()))
                            t.counter++;
                    }
                }
            }
        });

        Collections.sort(oTokens);

        // Removes 20% less frequent and more frequent tokens
        int percent = (int) (oTokens.size() * 0.2);
        for (int i = 0; i < percent; i++) {
            oTokens.remove(i);
            oTokens.remove(oTokens.size()-(i+1));
        }

        oTokens.forEach(oToken -> outList.add(oToken.token));
        oTokens.clear();

        return outList;
    }
}
