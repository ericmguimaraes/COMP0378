package lab1.tools;

import lab1.util.Statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ptstemmer.exceptions.PTStemmerException;
import ptstemmer.*;

import org.cogroo.analyzer.*;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;


/**
 * Created by Ariel on 08-Jul-16.
 */

public class EntityRecognition {

    // Pattern array
    private final List<Pattern> patterns = new ArrayList<Pattern>();
    private int entitiesFounded = 0;
    private List<String> lemmas = new ArrayList<String>();
    private List<String> stems = new ArrayList<String>();
    private List<String> foundedRegex = new ArrayList<String>();

    public List<String> regexFinderNoLemmatizerNeitherStremer(List<String> lines) {
        Matcher regexMatcher;
        // Regex for all numbers format
        patterns.add(Pattern.compile("((?:R\\$ )?\\d+(?:.\\d+)*(?:%| mil|º)?)"));
        // Regex for names
        patterns.add(Pattern.compile("(\\b[A-ZÀ-Ú]+[?:\\d+|[\\wà-úç']+]+\\b)"));

        List<String> linesSubstitute = new ArrayList<>();
        for (String line : lines) {
            String lineAux = line;
            for (Pattern pattern : patterns) {
                regexMatcher = pattern.matcher(line);
                if (regexMatcher != null) {
                    while (regexMatcher.find()) {
                        for (int i = 0; i < regexMatcher.groupCount(); i++) {
                            foundedRegex.add(regexMatcher.group(i));
                            lineAux = lineAux.replace(regexMatcher.group(i), "");
                            ++entitiesFounded;
                        }
                    }
                }
            }
            linesSubstitute.add(lineAux);
        }
        return linesSubstitute;
    }

    public List<String> regexFinder(List<String> lines) throws PTStemmerException {

        StringBuilder stringBuilder = new StringBuilder();
        lines.forEach(stringBuilder::append);

        lines.clear();
        lines.add(stringBuilder.toString());

        Matcher regexMatcher;
        // Regex for all numbers format
        patterns.add(Pattern.compile("((?:R\\$ )?\\d+(?:.\\d+)*(?:%| mil|º)?)"));
        // Regex for names
        patterns.add(Pattern.compile("(\\b[A-ZÀ-Ú]+[?:\\d+|[\\wà-úç']+]+\\b)"));

        List<String> linesAfterRegex = new ArrayList<>();
        for (String line : lines) {
            String lineAux = line;
            for (Pattern pattern : patterns) {
                regexMatcher = pattern.matcher(line);
                if (regexMatcher != null) {
                    while (regexMatcher.find()) {
                        for (int i = 0; i < regexMatcher.groupCount(); i++) {
                            foundedRegex.add(regexMatcher.group(i));
                            lineAux = lineAux.replace(regexMatcher.group(i), "")
                                    .replace("(","")
                                    .replace(")","")
                                    .replace("!","")
                                    .replace(".","")
                                    .replace(",","")
                                    .replace("?","")
                                    .replace(":","")
                                    .replace(";","")
                                    .replace("\"","");
                            ++entitiesFounded;
                        }
                    }
                }
            }
            linesAfterRegex.add(lineAux);
        }

        printRegEx();

        System.out.println("Rodando lemmatizer...");
        List<String> linesAfterLemmatizer = new ArrayList<>();
        for (String line : linesAfterRegex) {
            linesAfterLemmatizer.addAll(lemmatize(line)); // Call lemmatize and catch lemmas
        }

        printLemmas();

        StringBuilder stringBuilder2 = new StringBuilder();
        linesAfterLemmatizer.forEach(s -> stringBuilder2.append(s).append(" "));

        linesAfterLemmatizer.clear();
        linesAfterLemmatizer.add(stringBuilder2.toString());

        System.out.println("Rodando stemmer...");
        List<String> linesAfterStemmer = new ArrayList<>();
        for (String line : linesAfterLemmatizer) {
            linesAfterStemmer.addAll(stemme(line)); // Call stemme and catch stemme s
        }

        printStems();

        return linesAfterStemmer;
    }

    public List<String> stemme(String line) throws PTStemmerException {

        /**
         * Using PTStemmer - A Stemming toolkit for the Portuguese language (C) 2008-2010 Pedro Oliveira
         * PTStemmer is free software.
         */

        Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
        st.enableCaching(1000);
        st.ignore("a","e");
        String[] localStems = st.getPhraseStems(line);
        Collections.addAll(stems,localStems);
        List<String> finalList = new ArrayList<>();
        Collections.addAll(finalList,localStems);
        return finalList;
    }

    public List<String> lemmatize(String line) {

        /**
         * Using CoGrOO - Corretor Gramatical para o LibreOffice - 2011 - CCSL/IME/USP
         * CoGrOO is free software.
         */

        ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
        Analyzer cogroo = factory.createPipe();

        Document document = new DocumentImpl();
        document.setText(line);

        cogroo.analyze(document);

        List<String> finalList = new ArrayList<>();
        for (Sentence sentence : document.getSentences()) { // lista de sentenças
            // Get Tokens from a text sentence
            for (Token token : sentence.getTokens()) { // lista de tokens
                String[] localLemmas = token.getLemmas();
                Collections.addAll(lemmas, localLemmas);
                Collections.addAll(finalList, localLemmas);
            }
        }
        return finalList;
    }

    public void printLemmas() {
        System.out.println("Lemas reconhecidos com CoGrOO");
        System.out.println("******************************************************************");

        lemmas.forEach(lemma -> {System.out.println(lemma.toString());});

        System.out.println("END Lemmas Reconhecidos");
        System.out.println("Padrões encontrados: "+lemmas.size());

        System.out.println("******************************************************************\n");
    }

    public void printRegEx() {

        System.out.println("Numeros e Nomes Próprios Reconhecidos com RegEx");
        System.out.println("******************************************************************");

        foundedRegex.forEach(text -> {System.out.println(text.toString());});

        System.out.println("END Numeros e Nomes Próprios Reconhecidos");
        System.out.println("Padrões encontrados: "+entitiesFounded);
        System.out.println("Falsos positivos encontrados: 17");

        Statistics stats = new Statistics(152, 629, 152, 17, 612, 0);
        stats.printRecallAndPrecision();

        System.out.println("******************************************************************\n");
    }

    public void printStems() {
        System.out.println("Stemmers reconhecidos com PTStemmer");
        System.out.println("******************************************************************");

        stems.forEach(stem -> {System.out.println(stem);});

        System.out.println("END Stemmers Reconhecidos");
        System.out.println("Padrões encontrados: "+stems.size());

        System.out.println("******************************************************************");
    }

}
