package lab1;

import java.util.*;

/**
 * Created by ericm on 08-Jul-16.
 */
public class Tokenizer {

    public List<Token> linesToToken(List<String> lines){
        if(lines==null || lines.isEmpty())
            throw new RuntimeException("No lines to analyze");
        HashMap<String,Long> hashMap = getHashMap(lines);
        List<Token> tokens = new ArrayList<Token>();
        hashMap.forEach((k,v) -> {if(!k.equals(""))tokens.add(new Token(k,v));});
        Comparator<Token> byCounter = (t1, t2) -> Long.compare(t1.getCounter(), t2.getCounter());
        tokens.sort(byCounter);
        Collections.reverse(tokens);
        return tokens;
    }

    private HashMap<String,Long> getHashMap(List<String> lines) {
        HashMap<String,Long> hashMap = new HashMap<String, Long>();
        for(String line: lines){
            String[] words = line.replace(".", "").replace(",", "").replace("?", "").replace("!","").split("\\s+");
            for(String word: words){
                if(hashMap.containsKey(word)){
                    long counter = hashMap.get(word);
                    counter++;
                    hashMap.put(word,counter);
                } else {
                    hashMap.put(word, (long) 1);
                }
            }
        }
        return hashMap;
    }
}
