package lab3;

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

        //add idfs zero para termos nao existentes no doc
        docsPerTerm.forEach((s, aLong) ->
            documents.forEach(document -> {
                if(!document.getIdfs().containsKey(s))
                    document.getIdfs().put(s,0d);
            }));
    }

    public void printIDFMatrix(boolean headers) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(headers) {
            stringBuilder.append("classe;document;");
            documents.get(0).getSortedIDFKeys().forEach(s -> stringBuilder.append(s).append(";"));
            stringBuilder.append(System.getProperty("line.separator"));
        }
        documents.forEach(document -> {
            stringBuilder.append(document.getClasse()).append(";");
            if(headers)
                stringBuilder.append(document.getName()).append(";");
            document.getSortedIDFKeys().forEach(s -> {
                double aDouble = document.getIdfs().get(s);
                stringBuilder
                        .append(aDouble)
                        .append(";");
            });
            stringBuilder.append(System.getProperty("line.separator"));
        });
        FileManager fm = new FileManager();
        fm.writeToFile(headers?"IDF_Matrix_headers.csv":"IDF_Matrix.csv",stringBuilder.toString());
    }

}
