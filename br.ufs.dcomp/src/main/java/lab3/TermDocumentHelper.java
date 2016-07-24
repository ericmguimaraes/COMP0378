package lab3;

import lab1.util.FileManager;
import lab3.model.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ericm on 23-Jul-16.
 */
public class TermDocumentHelper {

    private List<Document> documents;

    private HashMap<String,Double> idfs;
    public boolean BTFMode = false; // Boolean TF for Boolean Naive Bayes

    public TermDocumentHelper(List<Document> documents) {
        this.documents = documents;
        idfs = new HashMap<>();
    }

    public void init(){
        populateTermFrequency();
        populateIDF();
        populateTermImportance();
        prepareToNaiveBayes();
    }

    private void populateTermFrequency() { documents.forEach(Document->{Document.init(BTFMode);}); }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public HashMap<String, Double> getIdfs() {
        return idfs;
    }

    public void setIdfs(HashMap<String, Double> idfs) {
        this.idfs = idfs;
    }

    public void setBTFMode(boolean BTFMode) { this.BTFMode = BTFMode; }

    private void populateIDF(){
        documents.forEach(document -> {
            document.getTermsMap().forEach((k, v) -> {
                if(idfs.containsKey(k))
                    idfs.put(k, idfs.get(k)+1);
                else
                    idfs.put(k,1d);
            });
        });
        idfs.forEach((s, v) -> idfs.put(s,Math.log(documents.size()/v)));
    }

    private void populateTermImportance(){
        documents.forEach(document -> {
            document.getTfs().forEach((s, aDouble) -> {
                double factor = idfs.get(s);
                document.getTermImportance().put(s,aDouble*factor);
            });
        });

        //add idfs zero para termos nao existentes no doc
        idfs.forEach((s, aLong) ->
            documents.forEach(document -> {
                if(!document.getTermImportance().containsKey(s))
                    document.getTermImportance().put(s,0d);
            }));
    }

    public void printImportanceMatrix(boolean headers) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(headers) {
            stringBuilder.append("classe;document;");
            documents.get(0).getSortedIDFKeys().forEach(s -> stringBuilder.append(s).append(";"));
            stringBuilder.append(System.getProperty("line.separator"));

            stringBuilder.append(System.getProperty("line.separator"));
            documents.get(0).getSortedIDFKeys().forEach(s -> stringBuilder.append(s).append(";"));
            stringBuilder.append(System.getProperty("line.separator"));
            documents.get(1).getSortedIDFKeys().forEach(s -> stringBuilder.append(s).append(";"));
            stringBuilder.append(System.getProperty("line.separator"));
        }
        documents.forEach(document -> {
            stringBuilder.append(document.getClasse()).append(";");
            if(headers)
                stringBuilder.append(document.getName()).append(";");
            document.getSortedIDFKeys().forEach(s -> {
                double aDouble = document.getTermImportance().get(s);
                stringBuilder
                        .append(aDouble)
                        .append(";");
            });
            stringBuilder.append(System.getProperty("line.separator"));
        });
        FileManager fm = new FileManager();
        fm.writeToFile(headers?"Importance_Matrix_headers.csv":"Importance_Matrix.csv",stringBuilder.toString());
    }

    public void printTFMatrix(boolean headers, String name) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(headers) {
            stringBuilder.append("classe;document;");
            stringBuilder.append(System.getProperty("line.separator"));
            documents.get(0).getSortedTFKeys().forEach(s -> stringBuilder.append(s).append(";"));
            stringBuilder.append(System.getProperty("line.separator"));
            documents.get(1).getSortedTFKeys().forEach(s -> stringBuilder.append(s).append(";"));
            stringBuilder.append(System.getProperty("line.separator"));
        }
        documents.forEach(document -> {
            stringBuilder.append(document.getClasse()).append(";");
            if(headers)
                stringBuilder.append(document.getName()).append(";");
            document.getSortedTFKeys().forEach(s -> {
                double aDouble = document.getTfs().get(s);
                stringBuilder
                        .append(aDouble)
                        .append(";");
            });
            stringBuilder.append(System.getProperty("line.separator"));
        });
        FileManager fm = new FileManager();
        if (name.contentEquals(""))
            fm.writeToFile(headers?"TF_Matrix_headers.csv":"TF_Matrix.csv",stringBuilder.toString());
        else
            fm.writeToFile(headers?name+"_headers.csv":name+".csv",stringBuilder.toString());
    }

    public void prepareToNaiveBayes(){
        idfs.forEach((s, aLong) ->
                documents.forEach(document -> {
                    if(!document.getTfs().containsKey(s))
                        document.getTfs().put(s,0d);
                }));
    }

}
