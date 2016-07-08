import lab1.util.FileManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericm on 08-Jul-16.
 */
public class FileManagerTest {

    @Test
    public void testWriteAndReadFromFile() throws IOException {
        String filename = "test.txt";
        List<String> linesToWrite = new ArrayList<String>();
        List<String> response;

        linesToWrite.add("oi");
        linesToWrite.add("teste");
        linesToWrite.add("123");
        FileManager fm = new FileManager();
        fm.writeToFile(filename,linesToWrite);
        response = fm.readFile(filename);

        Assert.assertTrue(response.size()==linesToWrite.size());

    }

    @Test
    public void testReWriteFile() throws IOException {
        String filename = "test.txt";
        List<String> linesToWrite = new ArrayList<String>();
        List<String> response;

        linesToWrite.add("oi");
        linesToWrite.add("teste");

        FileManager fm = new FileManager();
        fm.writeToFile(filename,linesToWrite);
        response = fm.readFile(filename);

        Assert.assertTrue(response.size()==linesToWrite.size());

        linesToWrite.add("123");

        fm.writeToFile(filename,linesToWrite);
        response = fm.readFile(filename);

        Assert.assertTrue(response.size()==linesToWrite.size());

    }

}
