package lab3;

import lab1.tools.EntityRecognition;
import lab1.util.FileManager;
import lab3.model.Document;
import lab3.util.MachineLearningHandler;
import lab3.tools.*;

import java.io.IOException;
import java.util.ArrayList;

import ptstemmer.exceptions.PTStemmerException;

import java.util.List;

/**
 * Created by Ariel on 22-Jul-16.
 */

public class Main {

    public static void main(String[] args) throws IOException, PTStemmerException {

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

        System.out.println("\nTarefa 1: ");

        // Tarefa 01
        MachineLearningHandler machineLearningHandler = new MachineLearningHandler();
        TermDocumentHelper termDocumentHelper = new TermDocumentHelper(documents);
        System.out.println("******Preparando Matriz de Importancia TermoXDocumento******");
        termDocumentHelper.init();
        termDocumentHelper.printImportanceMatrix(false);
        termDocumentHelper.printImportanceMatrix(true);
        machineLearningHandler.knn("Importance_Matrix.csv",0,1,true);

        System.out.println("\nTarefa 2: ");

        //Tarefa 02
        System.out.println("******Preparando Matriz de TF TermoXDocumento******");
        termDocumentHelper.printTFMatrix(true, "");
        termDocumentHelper.printTFMatrix(false, "");
        machineLearningHandler.naiveBayes("TF_Matrix.csv",0,true);

        System.out.println("\nTarefa 3: ");

        //Tarefa 03
        FileManager fm2 = new FileManager();
        List<Document> reviews = new ArrayList<>();
        System.out.println("******Lendo reviews******");
        reviews.add(new Document("positiva1", fm2.readFileToString("corpus/corpus_filmow_positivas.txt"), "Reviews Positivas"));
        reviews.add(new Document("positiva2", fm2.readFileToString("corpus/corpus_adorocinema_positivas.txt"), "Reviews Positivas"));
        reviews.add(new Document("negativa1", fm2.readFileToString("corpus/corpus_filmow_negativas.txt"), "Reviews Negativas"));
        reviews.add(new Document("negativa2", fm2.readFileToString("corpus/corpus_adorocinema_negativas.txt"), "Reviews Negativas"));
        termDocumentHelper = new TermDocumentHelper(reviews);
        termDocumentHelper.setBTFMode(true);
        termDocumentHelper.init();
        System.out.println("******Preparando Matriz de TF TermoXDocumento******");
        termDocumentHelper.printTFMatrix(true, "TF_Matrix2");
        termDocumentHelper.printTFMatrix(false, "TF_Matrix2");
        machineLearningHandler.naiveBayes("TF_Matrix2.csv",0,true);
    }

}
