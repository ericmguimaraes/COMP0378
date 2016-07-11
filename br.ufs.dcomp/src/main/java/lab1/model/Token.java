package lab1.model;

/**
 * Created by ericm on 08-Jul-16.
 */
public class Token {

    private String text;

    private long counter;

    public Token(){}

    public Token(String text){
        this.text = text;
        counter = 1;
    }

    public Token(String text, long counter) {
        this.text = text;
        this.counter = counter;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text+":"+counter;
    }
}
