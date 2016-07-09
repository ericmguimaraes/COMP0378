package lab1;

import lab1.tools.EntityRecognition;
import lab1.util.FileManager;
import ptstemmer.exceptions.PTStemmerException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ericm on 06-Jul-16.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length==0)
            throw new IllegalArgumentException("Informe o nome do arquivo a ser analizado.");
        FileManager fileManager = new FileManager();
        Tokenizer tokenizer = new Tokenizer();
        EntityRecognition entityRecognition = new EntityRecognition();
        List<String> lines = fileManager.readFile(args[0]);
        entityRecognition.regexFinder(lines);
        List<Token> tokens = tokenizer.linesToToken(lines);
        tokens.forEach(token -> {
            System.out.println(token.toString());
        });
    }

}
