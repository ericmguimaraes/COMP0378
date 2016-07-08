package lab1.util;

/**
 * Created by ericm on 08-Jul-16.
 */
public class Statistics {

    private long positives;

    private long negatives;

    private long truePositives;

    private long falsePositives;

    private long trueNegatives;

    private long falseNegatives;


    public Statistics(long positives, long negatives, long truePositives, long falsePositives, long trueNegatives, long falseNegatives) {
        this.positives = positives;
        this.negatives = negatives;
        this.truePositives = truePositives;
        this.falsePositives = falsePositives;
        this.trueNegatives = trueNegatives;
        this.falseNegatives = falseNegatives;
    }

    public float getRecall(){
        return truePositives / (truePositives+falsePositives);
    }

    public float getPrecision(){
        return truePositives / (truePositives+falseNegatives);
    }

    public float getSensitivity(){
        return trueNegatives / (truePositives+falseNegatives);
    }

    public float getSpecificity(){
        return trueNegatives / (falsePositives+trueNegatives);
    }

    public float getAccuracy(){
        return (truePositives+trueNegatives)/ (positives+negatives);
    }

    public void printRecallAndPrecision(){
        System.out.println("Recall: "+getRecall()*100+"% Precision:"+getPrecision()*100+"%");
    }

}
