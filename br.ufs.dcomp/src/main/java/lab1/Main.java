package lab1;

import lab1.tools.EntityRecognition;
import lab1.tools.MinimumEditDistance;
import lab1.tools.Tokenizer;
import lab1.util.FileManager;
import lab1.util.Noise;
import ptstemmer.exceptions.PTStemmerException;

import java.io.IOException;
import java.util.List;

/**
 * Created by ericm on 06-Jul-16.
 */
public class Main {

    public static void main(String[] args) throws IOException, PTStemmerException {
        if(args.length==0)
            throw new IllegalArgumentException("Informe o nome do arquivo a ser analizado.");
        FileManager fileManager = new FileManager();
        Tokenizer tokenizer = new Tokenizer();
        EntityRecognition entityRecognition = new EntityRecognition();
        List<String> lines = fileManager.readFile(args[0]);

        String textOriginal = entityRecognition.appendList(lines);

        //Parte 1 - Regex

        String textWithRegex = entityRecognition.regexFinder(textOriginal);

        //Parte 2 - Tokenizer

        tokenizer.textToTokens(textWithRegex);

        //Parte 3 - apply lemmatizer and stemmer

        String textWithLemmatizer = entityRecognition.lemmatize(textWithRegex);

        String textWithStemmer = entityRecognition.stemme(textWithLemmatizer);

        tokenizer.textToTokens(textWithStemmer);

        //Parte 4 - Ruido e Menor Distancia

        Noise noise = new Noise();

        String textWithNoise = noise.applyNoise(textWithRegex);

        MinimumEditDistance minimumEditDistance = new MinimumEditDistance();

        minimumEditDistance.minDistance(textWithNoise,textWithRegex);


    }

}
