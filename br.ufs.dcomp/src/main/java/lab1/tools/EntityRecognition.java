package lab1.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cogroo.text.Token;

import org.cogroo.text.impl.TokenImpl;
import ptstemmer.exceptions.PTStemmerException;
import ptstemmer.*;

import dictionary.DictionaryLoadException;
import org.xml.sax.SAXException;
import lemma.LemmatizeException;
import lemma.Lemmatizer;
import rank.WordRankingLoadException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Ariel on 08-Jul-16.
 */

public class EntityRecognition {

    // Pattern array
    private final List<Pattern> patterns = new ArrayList<Pattern>();
    private int entitiesFounded = 0;

    public int regexFinder(List<String> lines) {

        Matcher regexMatcher;

        // Regex for all numbers format
        patterns.add(Pattern.compile("((?:R\\$ )?\\d+(?:.\\d+)*(?:%| mil|º)?)"));
        // Regex for names
        patterns.add(Pattern.compile("(\\b[A-ZÀ-Ú]+[?:\\d+|[\\wà-úç']+]+\\b)"));

        for (String line : lines) {
            for (Pattern pattern : patterns) {
                regexMatcher = pattern.matcher(line);
                if (regexMatcher != null) {
                    while (regexMatcher.find()) {
                        for (int i = 0; i < regexMatcher.groupCount(); i++) {
                            ++entitiesFounded;
                        }
                    }
                }
            }
        }
        return entitiesFounded;
    }

    public String Stremmer(String word) throws PTStemmerException {

        /**
         * Using PTStemmer - A Stemming toolkit for the Portuguese language (C) 2008-2010 Pedro Oliveira
         *PTStemmer is free software.
         */

        Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
        st.enableCaching(1000);
        st.ignore("a","e");
        String Strem = st.getWordStem(word);

        return Strem;
    }

    public String Lemmatizer (String token)
            throws LemmatizeException, ParserConfigurationException, WordRankingLoadException, SAXException, DictionaryLoadException, IOException {
        String[] tags = {"v-fin", "art", "n", "art", "n", "adj", "punc", "v-fin",
                "n", "conj-c", "v-fin", "n", "punc"};
        Lemmatizer lemmatizer = new Lemmatizer();
        String lemma = lemmatizer.lemmatize(token, tags);

        return lemma;
    }

    public String getLemma (String token) {

    }

}
