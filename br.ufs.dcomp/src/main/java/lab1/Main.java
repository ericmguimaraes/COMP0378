package lab1;

import lab1.tools.EntityRecognition;
import lab1.util.FileManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by ericm on 06-Jul-16.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length==0)
            throw new IllegalArgumentException("Informe o nome do arquivo a ser analizado.");
        nlpnolemmatizerneitherstremer(args);
        nlp(args);
    }

    public static void nlpnolemmatizerneitherstremer(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        Tokenizer tokenizer = new Tokenizer();
        EntityRecognition entityRecognition = new EntityRecognition();
        List<String> lines = fileManager.readFile(args[0]);
        lines = entityRecognition.regexFinderNoLemmatizerNeitherStremer(lines);
        entityRecognition.printRegEx();
        List<Token> tokens = tokenizer.linesToToken(lines);
        tokens.forEach(token -> {
            System.out.println(token.toString());
        });
    }

    public static void nlp(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        Tokenizer tokenizer = new Tokenizer();
        EntityRecognition entityRecognition = new EntityRecognition();
        List<String> lines = fileManager.readFile(args[0]);
        lines = entityRecognition.regexFinder(lines);
        entityRecognition.printRegEx();
        List<Token> tokens = tokenizer.linesToToken(lines);
        tokens.forEach(token -> {
            System.out.println(token.toString());
        });
    }

}
