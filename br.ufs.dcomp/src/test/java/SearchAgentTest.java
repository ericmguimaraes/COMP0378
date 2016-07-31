import lab3.tools.*;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ariel on 24-Jul-16.
 */

public class SearchAgentTest {

    @Test
    public void main() throws IOException {
        //SearchAgent.generateCorpus();
        System.out.println(SearchAgent.getHits("bing", "(\"muito bom\" OR \"legal\" OR \"recomendo\")"));
        System.out.println(SearchAgent.getHits("bing", "(\"ruim\" OR \"chato\" OR \"cansativo\" OR \"não recomendo\")"));
    }

}
