package lab1;
import lab1.util.FileManager;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica.
 */
public class Ruido {

    public static void main(String args[]) throws IOException {
        String alfabeto = "abcdefghijklmnopqrstuvxwyzABCDEFGHIJKLMNOPQRSTUVXWYZ";
        int tam = alfabeto.length();
        FileManager fileManager = new FileManager();
        List<String> lines = fileManager.readFile(args[0]);
        List<String> aux = new ArrayList<>();
        Random r = new Random();

        for(String str : lines){
            int i = str.length() / 10;
            StringBuilder strBuilder = new StringBuilder(str);
            while (i>0){
                strBuilder.insert(10*i, alfabeto.charAt(r.nextInt(tam)));
                i--;
            }
            aux.add(strBuilder.toString());
        }

        if (fileManager.writeToFile("saida.txt", aux)){
            System.out.println("OK Mano");
        }

    }
}
