import lab3.TermDocumentHelper;
import lab3.model.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericm on 23-Jul-16.
 */
public class TermDocumentHelperTest {

    @Test
    public void tfTest() throws IOException {
        String classe = "";
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("doc teste1","system human system engineering testing EPS", classe));
        TermDocumentHelper termDocumentHelper = new TermDocumentHelper(documents);
        termDocumentHelper.init();
        termDocumentHelper.getDocuments().get(0).getTfs().forEach((s, aDouble) -> {
            if(s.equals("system")){
                Double expected = (double) 2/6;
                Assert.assertTrue(aDouble.equals(expected));
            }
        });
    }

    @Test
    public void IDFfTest() throws IOException {
        String classe = "classe";
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("doc teste1","humman machine interface for computer applications", classe));
        documents.add(new Document("doc teste2","a survey user opinion of computer system response time", classe));
        documents.add(new Document("doc teste3","the EPS user interface management system", classe));
        documents.add(new Document("doc teste4","system human system engineering testing EPS", classe));
        documents.add(new Document("doc teste5","the generation of random binary and ordered trees", classe));
        documents.add(new Document("doc teste6","the intersection graph of paths in trees", classe));
        documents.add(new Document("doc teste7","graph minors a survey", classe));
        TermDocumentHelper termDocumentHelper = new TermDocumentHelper(documents);
        termDocumentHelper.init();
        termDocumentHelper.printImportanceMatrix(true);
        termDocumentHelper.printImportanceMatrix(false);
    }




}
