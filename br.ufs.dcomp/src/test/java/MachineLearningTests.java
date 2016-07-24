import lab3.util.MachineLearningHandler;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ericm on 23-Jul-16.
 */
public class MachineLearningTests {

    @Test
    public void knnTest() throws IOException {
        MachineLearningHandler machineLearningHandler = new MachineLearningHandler();
        machineLearningHandler.knn("IDF_Matrix.csv",0,3,true);
    }

}
