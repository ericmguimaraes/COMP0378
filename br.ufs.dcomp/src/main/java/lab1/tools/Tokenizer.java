package lab1.tools;

import lab1.model.Token;

import java.util.*;

/**
 * Created by ericm on 08-Jul-16.
 */
public class Tokenizer {

    public List<Token> textToTokens(String text){
        if(text==null || text.isEmpty())
            throw new RuntimeException("No lines to analyze");
        HashMap<String,Long> hashMap = getHashMap(text);
        List<Token> tokens = new ArrayList<Token>();
        hashMap.forEach((k,v) -> {if(!k.equals(""))tokens.add(new Token(k,v));});
        Comparator<Token> byCounter = (t1, t2) -> Long.compare(t1.getCounter(), t2.getCounter());
        tokens.sort(byCounter);
        Collections.reverse(tokens);
        printTokens(tokens);
        return tokens;
    }

    private HashMap<String,Long> getHashMap(String text) {
        HashMap<String,Long> hashMap = new HashMap<String, Long>();
        text = text.replace("(","")
                .replace(")","")
                .replace("!","")
                .replace(".","")
                .replace(",","")
                .replace("?","")
                .replace(":","")
                .replace(";","")
                .replace("\"","");

            String[] words = text.split("\\s+");
            for(String word: words){
                if(hashMap.containsKey(word)){
                    long counter = hashMap.get(word);
                    counter++;
                    hashMap.put(word,counter);
                } else {
                    hashMap.put(word, (long) 1);
                }
            }

        return hashMap;
    }

    public void printTokens(List<Token> tokens){
        System.out.println("*****************************************");
        System.out.println("Tokens");
        tokens.forEach(token -> {
            System.out.println(token.toString());
        });
        System.out.println("*****************************************");
    }
}
