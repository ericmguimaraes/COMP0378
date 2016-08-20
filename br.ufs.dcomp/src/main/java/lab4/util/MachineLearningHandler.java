package lab4.util;

import lab1.util.Statistics;
import lab3.model.Document;

import java.util.List;

/**
 * Created by Ariel on 31-Jul-16.
 */
public class MachineLearningHandler {

    public void getStatistics(List<Document> documents, List<String> classes) {
        long results[] = {0, 0, 0, 0}; //results[0] = nTrue, results[1] = nWrong, results[2] = nTruePositives, results[3] = nFalsePositives

        documents.forEach(document -> {
            if (classes.remove(0).equals(document.getClasse())) {
                results[0]++;
                if (document.getClasse() == "positivas")
                    results[2]++;
            } else {
                System.out.println("Erro ao classificar instancia: " + (results[0] + results[1]));
                results[1]++;
                if (document.getClasse() == "negativas")
                    results[3]++;
            }
        });

        System.out.println("\n------Calculando taxa de erros e acertos------\n");
        Statistics statisticas = new Statistics(results[0], results[1], results[2],
                results[3], results[0]-results[2], results[1]-results[3]);

        System.out.println("Correct predictions:  " + results[0]);
        System.out.println("Wrong predictions: " + results[1]);
        System.out.println("True Positive Rate: " + results[2]);
        System.out.println("True Negative Rate: " + (results[1] - results[3]));
        statisticas.printRecallAndPrecision();
        System.out.println("Accuracy: " + statisticas.getAccuracy() + "%");
        //    System.out.println("F-Measure: ");
    }
}
