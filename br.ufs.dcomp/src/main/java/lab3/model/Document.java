package lab3.model;

import lab1.model.Token;
import lab1.tools.Tokenizer;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ericm on 23-Jul-16.
 */
public class Document {

    private String name;

    private String text;

    private HashMap<String,Long> termsMap;

    private long totalTermos;

    private HashMap<String,Double> tfs;

    private HashMap<String,Double> idfs;

    public Document(String name, String text) {
        this.name = name;
        this.text = text;
        tfs = new HashMap<>();
        idfs = new HashMap<>();
    }

    public void init(){
        Tokenizer tokenizer = new Tokenizer();
        termsMap = tokenizer.getHashMap(text);
        calculateTotal();
        calculateTF();
    }

    private void calculateTF(){
        termsMap.forEach((k, v) -> tfs.put(k, ((double) v / totalTermos)));
    }

    private void calculateTotal() {
        totalTermos = 0;
        termsMap.forEach((k, v) -> totalTermos+=v);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<String, Long> getTermsMap() {
        return termsMap;
    }

    public void setTermsMap(HashMap<String, Long> termsMap) {
        this.termsMap = termsMap;
    }

    public long getTotalTermos() {
        return totalTermos;
    }

    public void setTotalTermos(long totalTermos) {
        this.totalTermos = totalTermos;
    }

    public HashMap<String, Double> getTfs() {
        return tfs;
    }

    public void setTfs(HashMap<String, Double> tfs) {
        this.tfs = tfs;
    }

    public HashMap<String, Double> getIdfs() {
        return idfs;
    }

    public void setIdfs(HashMap<String, Double> idfs) {
        this.idfs = idfs;
    }
}
