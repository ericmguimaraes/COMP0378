package lab1;

import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica.
 */
public class Noise {

    public String applyNoise(String text) throws IOException {
        String alfabeto = "abcdefghijklmnopqrstuvxwyzABCDEFGHIJKLMNOPQRSTUVXWYZ";
        int tam = alfabeto.length();

        Random r = new Random();

        int i = text.length() / 15;
        StringBuilder strBuilder = new StringBuilder(text);
        while (i>0){
            strBuilder.insert(r.nextInt(text.length()-1), alfabeto.charAt(r.nextInt(tam)));
            i--;
        }

        System.out.println(strBuilder.toString());

        return strBuilder.toString();
    }
}
