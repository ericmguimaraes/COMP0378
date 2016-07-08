package lab1.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericm on 08-Jul-16.
 */
public class FileManager {

    public List<String> readFile(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
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

}
