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

    public String appendList(List<String> lines){
        StringBuilder stringBuilder = new StringBuilder();
        lines.forEach(s -> stringBuilder.append(s).append(" "));
        return stringBuilder.toString();
    }

    public String regexFinder(List<String> lines) throws PTStemmerException {
        return regexFinder(appendList(lines));
    }

    public String regexFinder(String text) throws PTStemmerException {

        Matcher regexMatcher;
        // Regex for all numbers format
        patterns.add(Pattern.compile("((?:R\\$ )?\\d+(?:.\\d+)*(?:%| mil|º)?)"));
        // Regex for names
        patterns.add(Pattern.compile("(\\b[A-ZÀ-Ú]+[?:\\d+|[\\wà-úç']+]+\\b)"));

        for (Pattern pattern : patterns) {
            regexMatcher = pattern.matcher(text);
            if (regexMatcher != null)
                while (regexMatcher.find()) {
                    for (int i = 0; i < regexMatcher.groupCount(); i++) {
                        foundedRegex.add(regexMatcher.group(i));
                        text = text.replace(regexMatcher.group(i), "");
                        ++entitiesFounded;
                    }
                }
        }

        text = text.replace("(","")
                .replace(")","")
                .replace("!","")
                .replace(".","")
                .replace(",","")
                .replace("?","")
                .replace(":","")
                .replace(";","")
                .replace("\"","");

        printRegEx();

        return text;
    }

    public String stemme(String text) throws PTStemmerException {
        System.out.println("Rodando stemmer...");

        /**
         * Using PTStemmer - A Stemming toolkit for the Portuguese language (C) 2008-2010 Pedro Oliveira
         * PTStemmer is free software.
         */

        Stemmer st = Stemmer.StemmerFactory(Stemmer.StemmerType.ORENGO);
        st.enableCaching(1000);
        st.ignore("a","e");
        String[] localStems = st.getPhraseStems(text);
        Collections.addAll(stems,localStems);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:localStems)
            stringBuilder.append(s).append(" ");

        printStems(stringBuilder.toString());

        return stringBuilder.toString();
    }

    public String lemmatize(String text) {

        System.out.println("Rodando lemmatizer...");
        /**
         * Using CoGrOO - Corretor Gramatical para o LibreOffice - 2011 - CCSL/IME/USP
         * CoGrOO is free software.
         */

        ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));
        Analyzer cogroo = factory.createPipe();

        Document document = new DocumentImpl();
        document.setText(text);

        cogroo.analyze(document);

        StringBuilder stringBuilder = new StringBuilder();
        for (Sentence sentence : document.getSentences()) { // lista de sentenças
            // Get Tokens from a text sentence
            for (Token token : sentence.getTokens()) { // lista de tokens
                String[] localLemmas = token.getLemmas();
                Collections.addAll(lemmas, localLemmas);
                for(String s:localLemmas)
                    stringBuilder.append(s).append(" ");
            }
        }

        printLemmas(stringBuilder.toString());

        return stringBuilder.toString();
    }

    public void printLemmas(String lemmasString) {
        System.out.println("Lemas reconhecidos com CoGrOO");
        System.out.println("******************************************************************");

        if(lemmasString!=null && !lemmasString.isEmpty())
            System.out.println(lemmasString);
        else
            lemmas.forEach(System.out::println);

        System.out.println("END Lemmas Reconhecidos");
        System.out.println("Padrões encontrados: "+lemmas.size());

        System.out.println("******************************************************************\n");
    }

    public void printRegEx() {

        System.out.println("Numeros e Nomes Próprios Reconhecidos com RegEx");
        System.out.println("******************************************************************");

        foundedRegex.forEach(System.out::println);

        System.out.println("END Numeros e Nomes Próprios Reconhecidos");
        System.out.println("Padrões encontrados: "+entitiesFounded);
        System.out.println("Falsos positivos encontrados: 17");

        Statistics stats = new Statistics(152, 629, 152, 17, 612, 0);
        stats.printRecallAndPrecision();

        System.out.println("******************************************************************\n");
    }

    public void printStems(String stemsString) {
        System.out.println("Stemmers reconhecidos com PTStemmer");
        System.out.println("******************************************************************");

        if(stemsString!=null && !stemsString.isEmpty())
            System.out.println(stemsString);
        else
            stems.forEach(System.out::println);

        System.out.println("END Stemmers Reconhecidos");
        System.out.println("Padrões encontrados: "+stems.size());

        System.out.println("******************************************************************");
    }

}
