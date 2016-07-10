package lab1.tools;

import lab1.util.Statistics;

import java.util.ArrayList;
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

    public List<String> regexFinder(List<String> lines) {

        Matcher regexMatcher;
        // Regex for all numbers format
        patterns.add(Pattern.compile("((?:R\\$ )?\\d+(?:.\\d+)*(?:%| mil|º)?)"));
        // Regex for names
        patterns.add(Pattern.compile("(\\b[A-ZÀ-Ú]+[?:\\d+|[\\wà-úç']+]+\\b)"));

        List<String> linesSubstitute = new ArrayList<>();
        for (String line : lines) {
            String lineAux = line;
            System.out.println("Rodando Lemmatizer...");
            Lemmatizer(line); // Call Lemmatizer and catch lemmas
            try {
                System.out.println("Rodando Stemmer...");
                Stemmer(line); // Call Stemmer and catch stemme s
            } catch (PTStemmerException e) {
                e.printStackTrace();
            }
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

        printLemmas();
        printStems();

        return linesSubstitute;
    }

    public void Stemmer(String line) throws PTStemmerException {

        /**
         * Using PTStemmer - A Stemming toolkit for the Portuguese language (C) 2008-2010 Pedro Oliveira
         * PTStemmer is free software.
         */

        Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
        st.enableCaching(1000);
        st.ignore("a","e");
        for (String stemAux : st.getPhraseStems(line)) {
            stems.add(stemAux);
        }

    }

    public void Lemmatizer (String line) {

        /**
         * Using CoGrOO - Corretor Gramatical para o LibreOffice - 2011 - CCSL/IME/USP
         * CoGrOO is free software.
         */

        ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
        Analyzer cogroo = factory.createPipe();

        Document document = new DocumentImpl();
        document.setText(line);

        cogroo.analyze(document);

        for (Sentence sentence : document.getSentences()) { // lista de sentenças
            sentence.getStart(); sentence.getEnd(); // caracteres onde a sentença começa e termina
            sentence.getText(); // texto da sentença

            // Get Tokens from a text sentence
            for (Token token : sentence.getTokens()) { // lista de tokens
                for (String s : token.getLemmas())
                    lemmas.add(s); // adiciona os possíveis lemas para o par lexeme+postag na lista
                //System.out.println("Token: " + token.getLexeme() + " POSTag: " + token.getPOSTag() + " Features: " + token.getFeatures()); // classe morfológica de acordo com o contexto
                }
        }
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
