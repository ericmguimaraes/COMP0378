package lab3.model;

import lab1.tools.Tokenizer;

import java.util.*;

/**
 * Created by ericm on 23-Jul-16.
 */
public class Document {

    private String name;

    private String text;

    private HashMap<String,Long> termsMap;

    private long totalTermos;

    private HashMap<String,Double> tfs;

    private HashMap<String,Double> termImportance;

    private String classe;

    public Document(String name, String text, String classe) {
        this.name = name;
        this.text = text;
        this.classe = classe;
        tfs = new HashMap<>();
        termImportance = new HashMap<>();
    }

    public void init(boolean BTF){
        Tokenizer tokenizer = new Tokenizer();
        termsMap = tokenizer.getHashMap(text);
        calculateTotal();
        if (BTF) {
            calculateBTF();
        } else {
            calculateTF();
        }
    }

    private void calculateTF(){ termsMap.forEach((k, v) -> tfs.put(k, ((double) v / totalTermos)));  }

    private void calculateBTF(){ termsMap.forEach((k, v) -> tfs.put(k, ((double) 1))); }

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

    public HashMap<String, Double> getTermImportance() {
        return termImportance;
    }

    public void setTermImportance(HashMap<String, Double> termImportance) {
        this.termImportance = termImportance;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public SortedSet<String> getSortedIDFKeys() {
        return new TreeSet<String>(termImportance.keySet());
    }

    public SortedSet<String> getSortedTFKeys() {
        return new TreeSet<String>(tfs.keySet());
    }

}
