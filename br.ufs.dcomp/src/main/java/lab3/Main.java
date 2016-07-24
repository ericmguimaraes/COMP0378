package lab3;

import lab1.util.FileManager;
import lab3.model.Document;
import lab3.util.MachineLearningHandler;
import lab3.util.SearchAgent;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ariel on 22-Jul-16.
 */

public class Main {
    public static void main(String[] args) throws IOException {
       /* System.out.println("Google Search Agent: " + SearchAgent.getGoogleHits("vem monstro") + " results"
                + "\nFilmow Search Agent: " + SearchAgent.getReview("filmow", "procurando-dory-t61563")
                + "\nAdoro Cinema Search Agent: " + SearchAgent.getReview("adorocinema", "226078"));

        SearchAgent.generateCorpora(); */

        // Tarefa 01
        FileManager fm = new FileManager();
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("saude1",fm.readFileToString("corpus/corpus_saude1.txt"),"saude"));

        IDFHelper idfHelper = new IDFHelper(documents);

        MachineLearningHandler machineLearningHandler = new MachineLearningHandler();
        machineLearningHandler.knn("IDF_Matrix.csv",0,3,true);
    }
}
