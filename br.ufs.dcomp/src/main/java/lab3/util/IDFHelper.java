package lab3.util;

import lab1.util.FileManager;
import lab3.model.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ericm on 23-Jul-16.
 */
public class IDFHelper {

    private List<Document> documents;

    private HashMap<String,Long> docsPerTerm;

    public IDFHelper(List<Document> documents) {
        this.documents = documents;
        docsPerTerm = new HashMap<>();
    }

    public void init(){
        documents.forEach(Document::init);
        populateDocsPerTerm();
        populateIdfs();
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public HashMap<String, Long> getDocsPerTerm() {
        return docsPerTerm;
    }

    public void setDocsPerTerm(HashMap<String, Long> docsPerTerm) {
        this.docsPerTerm = docsPerTerm;
    }

    private void populateDocsPerTerm(){
        documents.forEach(document -> {
            document.init();
            document.getTermsMap().forEach((k, v) -> {
                if(docsPerTerm.containsKey(k))
                    docsPerTerm.put(k, docsPerTerm.get(k)+1);
                else
                    docsPerTerm.put(k,1L);
            });
        });
    }

    private void populateIdfs(){
        documents.forEach(document -> {
            document.getTfs().forEach((s, aDouble) -> {
                double factor = Math.log(documents.size()/docsPerTerm.get(s));
                document.getIdfs().put(s,aDouble*factor);
            });
        });
    }

    public void printMatrix() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        documents.forEach(document -> {
            document.getIdfs().forEach((s, aDouble) -> {
                stringBuilder.append(document.getName())
                        .append(" : ")
                        .append(s)
                        .append(" = ")
                        .append(aDouble)
                        .append(System.getProperty("line.separator"));
            });
        });
        FileManager fm = new FileManager();
        fm.writeToFile("IDF_Matrix.txt",stringBuilder.toString());
    }

}
