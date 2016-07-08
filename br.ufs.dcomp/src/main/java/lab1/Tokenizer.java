package lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ericm on 08-Jul-16.
 */
public class Tokenizer {

    public List<Token> linesToToken(List<String> lines){
        if(lines==null || lines.isEmpty())
            throw new RuntimeException("No lines to analyze");
        HashMap<String,Long> hashMap = getHashMap(lines);
        List<Token> tokens = new ArrayList<Token>();
        hashMap.forEach((k,v) -> tokens.add(new Token(k,v)));
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
