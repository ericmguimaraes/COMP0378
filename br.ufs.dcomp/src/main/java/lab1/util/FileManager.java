package lab1.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericm on 08-Jul-16.
 */
public class FileManager {

    public List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<String>();
        String line = null;
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            if(bufferedReader!=null)
                bufferedReader.close();
        }
        return lines;
    }

    public boolean writeToFile(String filename, List<String> lines) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(fileWriter);
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } finally {
            if(bufferedWriter!=null)
                bufferedWriter.close();
        }
        return true;
    }


}
