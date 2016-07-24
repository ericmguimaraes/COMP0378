import lab3.model.Document;
import lab3.util.IDFHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericm on 23-Jul-16.
 */
public class IDFHelperTest {

    @Test
    public void tfTest() throws IOException {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("doc teste1","system human system engineering testing EPS"));
        IDFHelper idfHelper = new IDFHelper(documents);
        idfHelper.init();
        idfHelper.getDocuments().get(0).getTfs().forEach((s, aDouble) -> {
            if(s.equals("system")){
                Double expected = (double) 2/6;
                Assert.assertTrue(aDouble.equals(expected));
            }
        });
    }

    @Test
    public void IDFfTest() throws IOException {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("doc teste1","humman machine interface for computer applications"));
        documents.add(new Document("doc teste2","a survey user opinion of computer system response time"));
        documents.add(new Document("doc teste3","the EPS user interface management system"));
        documents.add(new Document("doc teste4","system human system engineering testing EPS"));
        documents.add(new Document("doc teste5","the generation of random binary and ordered trees"));
        documents.add(new Document("doc teste6","the intersection graph of paths in trees"));
        documents.add(new Document("doc teste7","graph minors a survey"));
        IDFHelper idfHelper = new IDFHelper(documents);
        idfHelper.init();
        idfHelper.getDocuments().get(3).getIdfs().forEach((s, aDouble) -> {
            if(s.equals("system")){
                Double expected = 0.298d;
             //   Assert.assertTrue(aDouble.equals(expected));
            }
        });
    }




}
