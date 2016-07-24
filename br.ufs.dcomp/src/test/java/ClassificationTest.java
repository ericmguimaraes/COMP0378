/**
 * Created by Ariel on 23-Jul-16.
 */

import lab1.util.FileManager;
import lab3.tools.*;
import org.junit.Test;

import java.io.IOException;

public class ClassificationTest {

    @Test
    public void main() throws IOException {
        Classification classify = new Classification();

        for (String review: (new FileManager()).readFile("corpora.txt")) {
            classify.SemanticOrientation(review);
        }
    }

}
