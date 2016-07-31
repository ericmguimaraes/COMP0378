package lab3.util;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

import opennlp.maxent.GIS;
import opennlp.maxent.io.GISModelWriter;
import opennlp.maxent.io.SuffixSensitiveGISModelReader;
import opennlp.maxent.io.SuffixSensitiveGISModelWriter;
import opennlp.model.AbstractModel;
import opennlp.model.EventStream;
import opennlp.model.OnePassDataIndexer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by ericm on 23-Jul-16.
 */
public class MachineLearningHandler {

    public void knn(String fileName, int classPosition, int k, boolean metricas) throws IOException {
        System.out.println("------" + k + "NN------");

        Dataset data = FileHandler.loadDataset(new File(fileName), classPosition, ";");
        System.out.println("------Treinando------");
        Classifier knn = new KNearestNeighbors(k);
        knn.buildClassifier(data);
        System.out.println("OK");

        classify(data, knn, metricas);
    }

    public void naiveBayes(String file, int classPosition,
                           boolean metricas) throws IOException {
        System.out.println("------Naive Bayes------");
        Dataset data = FileHandler.loadDataset(new File(file), classPosition, ";");

		/* Discretize through EqualWidtBinning */
       /* EqualWidthBinning eb = new EqualWidthBinning(10);
        System.out.println("Start discretisation");
        eb.build(data);
        Dataset ddata = data.copy();
        eb.filter(ddata); */

        System.out.println("------Treinando------");
        boolean useLaplace = true;
        boolean useLogs = true;
        Classifier nbc = new NaiveBayesClassifier(useLaplace, useLogs, false);
        nbc.buildClassifier(data);
        System.out.println("OK");

        classify(data, nbc, metricas);
    }

    private void classify(Dataset dataForClassification, Classifier classificador, boolean metricas) throws IOException {
        System.out.println("------Classificando------");

        int correct = 0, wrong = 0;
        for (Instance inst : dataForClassification) {
            Object predictedClassValue = classificador.classify(inst);
            Object realClassValue = inst.classValue();
            if (predictedClassValue.equals(realClassValue))
                correct++;
            else {
                System.out.println("Erro ao classificar instancia: " + (correct + wrong));
                wrong++;
            }
        }
        System.out.println("Correct predictions  " + correct);
        System.out.println("Wrong predictions " + wrong);

        if (metricas) {
            System.out.println("******Rodando 6-fold******");
            CrossValidation cv = new CrossValidation(classificador);
            Map<Object, PerformanceMeasure> pm = cv.crossValidation(dataForClassification, 6);
            for (Object o : pm.keySet()) {
                System.out.println("------" + o + "------");
                System.out.println("Accuracy : " + pm.get(o).getAccuracy());
                System.out.println("Reccal : " + pm.get(o).getRecall());
                System.out.println("Precision : " + pm.get(o).getPrecision());
                System.out.println("True Positive Rate :" + pm.get(o).getTPRate());
                System.out.println("True Negative Rate :" + pm.get(o).getTNRate());
                System.out.println("F-Measure :" + pm.get(o).getFMeasure());
            }
        }
    }


    public AbstractModel METrainModel(EventStream eventStream, int iterations) {
        try {
            return GIS.trainModel(iterations, new OnePassDataIndexer(eventStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AbstractModel METrainModel(EventStream eventStream, int cutoff, int iterations) {
        try {
            return GIS.trainModel(iterations, new OnePassDataIndexer(eventStream, cutoff));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AbstractModel MELoadModel(String modelFileName) {
        try {
            return new SuffixSensitiveGISModelReader(new File(modelFileName + ".bin.gz")).getModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void MESaveModel(String modelFileName, AbstractModel model) {
        GISModelWriter modelWriter;
        try {
            modelWriter = new SuffixSensitiveGISModelWriter(model, new File(modelFileName + ".bin.gz"));
            modelWriter.persist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}