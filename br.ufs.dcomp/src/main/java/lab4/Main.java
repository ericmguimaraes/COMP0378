package lab4;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import lab1.util.FileManager;
import lab3.model.Document;
import lab4.util.MachineLearningHandler;
import lab4.util.TermDocumentHelper;
import opennlp.maxent.GIS;
import opennlp.maxent.io.GISModelReader;
import opennlp.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.model.AbstractModel;
import opennlp.model.AbstractModelWriter;
import opennlp.model.DataIndexer;
import opennlp.model.DataReader;
import opennlp.model.FileEventStream;
import opennlp.model.MaxentModel;
import opennlp.model.OnePassDataIndexer;
import opennlp.model.PlainTextFileDataReader;


/**
 * Created by Ariel on 29-Jul-16.
 * Classificador corrido mas MaxEnt não está funcionando bem, necessita rever implementação
 */
public class Main {

    public static void main(String[] args) throws IOException {

        // Parte 1

        // Read reviews and get 1000 most important by idf
        FileManager fm = new FileManager();
        List<Document> documents = new ArrayList<>();
        System.out.println("\n******Lendo reviews******");
        documents.add(new Document("positivas", fm.readFileToString("corpus/corpus_positives_reviews.txt"), "positivas"));
        documents.add(new Document("negativas", fm.readFileToString("corpus/corpus_negatives_reviews.txt"), "negativas"));

        System.out.println("\n******Gerando modelo de treinamento******");
        TermDocumentHelper termDocumentHelper = new TermDocumentHelper(documents);
        termDocumentHelper.init();
        termDocumentHelper.generateTrainingModel();

        System.out.println("\n******Treinando MaxEnt******\n");
        DataIndexer indexer = new OnePassDataIndexer(new FileEventStream("MaxEntTrainFiles.txt"));
        MaxentModel trainedMaxentModel = GIS.trainModel(1000, indexer); // 1.000 iterations

        System.out.println("\n******Salvando resultado do treinamento******");
        // Storing the trained model into a file for later use (gzipped)
        File outFile = new File("trained-model.maxent.gz");
        AbstractModelWriter writer = new SuffixSensitiveGISModelWriter((AbstractModel) trainedMaxentModel, outFile);
        writer.persist();

        System.out.println("\n******Preparando para classificar reviews******\n");
        // Loading the gzipped model from a file
        FileInputStream inputStream = new FileInputStream("trained-model.maxent.gz");
        InputStream decodedInputStream = new GZIPInputStream(inputStream);
        DataReader modelReader = new PlainTextFileDataReader(decodedInputStream);
        MaxentModel loadedMaxentModel = new GISModelReader(modelReader).getModel();

        // Get 10 more important features
        termDocumentHelper.getMostImportantFeatures(10);

        System.out.println("\n******Classificando******");
        // Predects the outcome using the loaded model
        List<Document> documentsForTest = new ArrayList<>();
        List<String> classes = new ArrayList<>(), positiveReviews = fm.readFile("corpus/positive_reviews_forTest.txt"),
                negativeReviews = fm.readFile("corpus/negative_reviews_forTest.txt");
        positiveReviews.forEach(review -> documentsForTest.add(new Document("TestReviewPositiva", review, "positivas")));
        positiveReviews.forEach(review -> documentsForTest.add(new Document("TestReviewNegativa", review, "negativas")));

        new TermDocumentHelper(documentsForTest).init();

        documentsForTest.forEach(document -> {
            String[] context = {document.getSortedTFKeys().toString()};
            double[] outcomeProbs = loadedMaxentModel.eval(context);
            String outcome = loadedMaxentModel.getBestOutcome(outcomeProbs);
            System.out.println(loadedMaxentModel.getAllOutcomes(outcomeProbs));
            classes.add(outcome.toString().replace("class=", ""));
        });
        System.out.println("\n******Gerando Estatísticas******\n");
        new MachineLearningHandler().getStatistics(documentsForTest, classes);

    }

}