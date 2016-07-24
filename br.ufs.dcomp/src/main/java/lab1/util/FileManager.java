package lab1.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericm on 08-Jul-16.
 */
public class FileManager {

    public String readFileToString(String filename) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> lines = readFile(filename);
        lines.forEach(s -> stringBuilder.append(s).append(System.getProperty("line.separator")));
        return stringBuilder.toString();
    }

    public List<String> readFile(String filename) throws IOException {
        List<String> lines;
        try {
            lines=Files.readAllLines(Paths.get(filename), Charset.forName("Cp1252"));
        } catch (MalformedInputException e){
            lines=Files.readAllLines(Paths.get(filename));
        }
        return lines;
    }

    public boolean writeToFile(String filename, List<String> lines) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
        return true;
    }

    public boolean writeToFile(String filename, String text) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            writer.write(text);
        }
        return true;
    }

}
