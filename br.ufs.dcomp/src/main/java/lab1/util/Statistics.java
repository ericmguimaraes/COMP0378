package lab1.util;

import java.text.DecimalFormat;

/**
 * Created by ericm on 08-Jul-16.
 */
public class Statistics {

    private double positives;

    private double negatives;

    private double truePositives;

    private double falsePositives;

    private double trueNegatives;

    private double falseNegatives;


    public Statistics(long positives, long negatives, long truePositives, long falsePositives, long trueNegatives, long falseNegatives) {
        this.positives = positives;
        this.negatives = negatives;
        this.truePositives = truePositives;
        this.falsePositives = falsePositives;
        this.trueNegatives = trueNegatives;
        this.falseNegatives = falseNegatives;
    }

    public double getRecall(){
        return (truePositives / (truePositives+falsePositives)) * 100;
    }

    public double getPrecision(){
        return (truePositives / (truePositives+falseNegatives)) * 100;
    }

    public double getSensitivity(){
        return trueNegatives / (truePositives+falseNegatives) * 100;
    }

    public double getSpecificity(){
        return trueNegatives / (falsePositives+trueNegatives) * 100;
    }

    public double getAccuracy(){
        return (truePositives+trueNegatives)/ (positives+negatives) * 100;
    }

    public void printRecallAndPrecision(){
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("Recall: "+df.format(getRecall())+"% Precision:"+df.format(getPrecision())+"%");
    }

}
