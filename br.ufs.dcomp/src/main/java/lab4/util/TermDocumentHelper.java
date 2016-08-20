package lab4.util;

import lab1.util.FileManager;
import lab3.model.Document;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ariel on 31-Jul-16.
 */

public class TermDocumentHelper {

    private List<Document> documents;
    private HashMap<String,Long> idfs;

    public TermDocumentHelper(List<Document> documents) {
        this.documents = documents;
        idfs = new HashMap<>();
    }

    public void init(){
        populateTermFrequency();
        populateIDF();
        populateTermImportance();
    }

    public void generateTrainingModel () throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Long> terms =
                idfs.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                        .limit(1000)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        documents.forEach(document -> {
            stringBuilder.append("class="+document.getName()).append(" ");
            for (Map.Entry<String, Long> term : terms.entrySet()) {
                long aLong = Math.round(document.getTermImportance().get(term.getKey()));
                if (aLong == 0)
                    continue;
                stringBuilder.append(term.getKey()+"="+aLong).append(" ");
            }
            stringBuilder.append(System.getProperty("line.separator"));
        });

        (new FileManager()).writeToFile("MaxEntTrainFiles.txt", stringBuilder.toString());
    }

    public void getMostImportantFeatures (int i) {
        StringBuilder stringBuilder = new StringBuilder();

        Map<String, Long> terms =
                idfs.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                        .limit(1000)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        documents.forEach(document -> {
            int aux = i;
            for (Map.Entry<String, Long> term : terms.entrySet()) {
                long aLong = Math.round(document.getTermImportance().get(term.getKey()));
                if (aLong == 0)
                    continue;
                --aux;
                if (aux < 0)
                    break;
                stringBuilder.append(term.getKey()+"="+aLong).append(" ");
            }
            System.out.println("Reviews "+document.getClasse()+" : " + stringBuilder.toString());
            stringBuilder.setLength(0);
        });
    }

    private void populateTermFrequency() {
        documents.forEach(document-> document.init(false));
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public HashMap<String, Long> getIdfs() {
        return idfs;
    }
    
    public Long getIDF (String term) {
        if (idfs.containsKey(term))
            return idfs.get(term);
        else
            return 0l;
    }

    public void setIdfs(HashMap<String, Long> idfs) {
        this.idfs = idfs;
    }

    private void populateIDF(){
        documents.forEach(document -> {
            document.getTermsMap().forEach((k, v) -> {
                if(idfs.containsKey(k))
                    idfs.put(k, idfs.get(k)+1);
                else
                    idfs.put(k,1l);
            });
        });
        //idfs.forEach((s, v) -> idfs.put(s,Math.log(documents.size()/v)));
        idfs.forEach((s, v) -> idfs.put(s,v));
    }

    private void populateTermImportance(){
        documents.forEach(document -> {
            document.getTfs().forEach((s, aLong) -> {
                Long factor = idfs.get(s);
                document.getTermImportance().put(s, Double.valueOf(factor));//aLong*factor);
            });
        });

        //add idfs zero para termos nao existentes no doc
        idfs.forEach((s, aLong) ->
                documents.forEach(document -> {
                    if(!document.getTermImportance().containsKey(s))
                        document.getTermImportance().put(s,0d);
                }));
    }

}
