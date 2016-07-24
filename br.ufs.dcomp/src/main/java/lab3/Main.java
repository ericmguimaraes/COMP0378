package lab3;

import lab1.tools.EntityRecognition;
import lab1.util.FileManager;
import lab3.model.Document;
import lab3.util.MachineLearningHandler;

import java.io.IOException;
import java.util.ArrayList;

import ptstemmer.exceptions.PTStemmerException;

import java.util.List;

/**
 * Created by Ariel on 22-Jul-16.
 */

public class Main {

    public static void main(String[] args) throws IOException, PTStemmerException {
       /* System.out.println("Google Search Agent: " + SearchAgent.getGoogleHits("vem monstro") + " results"
                + "\nFilmow Search Agent: " + SearchAgent.getReview("filmow", "procurando-dory-t61563")
                + "\nAdoro Cinema Search Agent: " + SearchAgent.getReview("adorocinema", "226078"));


        SearchAgent.generateCorpora();

        Classification classifier = new Classification();

        for (String review: (new FileManager()).readFile("corpora.txt")) {
            classifier.SemanticOrientation(review);
        }

        */

        // Tarefa 01
        FileManager fm = new FileManager();
        List<Document> documents = new ArrayList<>();
        System.out.println("******Lendo arquivos******");
        documents.add(new Document("saude1",fm.readFileToString("corpus/corpus_saude1.txt"),"saude"));
        documents.add(new Document("saude2",fm.readFileToString("corpus/corpus_saude2.txt"),"saude"));
        documents.add(new Document("tecnologia1",fm.readFileToString("corpus/corpus_tecnologia1.txt"),"tecnologia"));
        documents.add(new Document("tecnologia2",fm.readFileToString("corpus/corpus_tecnologia2.txt"),"tecnologia"));
        documents.add(new Document("negocios1",fm.readFileToString("corpus/corpus_negocios1.txt"),"negocios"));
        documents.add(new Document("negocios2",fm.readFileToString("corpus/corpus_negocios2.txt"),"negocios"));
        TermDocumentHelper termDocumentHelper = new TermDocumentHelper(documents);
        System.out.println("******Preparando Matriz de Importancia TermoXDocumento******");
        termDocumentHelper.init();
        termDocumentHelper.printImportanceMatrix(false);
        termDocumentHelper.printImportanceMatrix(true);
        MachineLearningHandler machineLearningHandler = new MachineLearningHandler();
        machineLearningHandler.knn("Importance_Matrix.csv",0,1,true);
        System.out.println("******Preparando Matriz de TF TermoXDocumento******");
        termDocumentHelper.printTFMatrix(true);
        termDocumentHelper.printTFMatrix(false);
        machineLearningHandler.naiveBayes("TF_Matrix.csv",0,true);

    }

}
