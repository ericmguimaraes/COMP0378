package lab1.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ariel on 08-Jul-16.
 */

public class EntityRecognition {

    // Pattern array
    private final List<Pattern> patterns = new ArrayList<Pattern>();
    private int numbersFounded = 0;

    public int regexFinder(List<String> lines) {

        Matcher regexMatcher;

        // Regex for all numbers format
        patterns.add(Pattern.compile("((?:R\\$ )?\\d+(?:.\\d+)*(?:%| mil|º)?)"));
        // Regex for names
        patterns.add(Pattern.compile("(\\b[A-ZÀ-Ú]+[?:\\d+|[\\wà-úç']+]+\\b)"));

        for (String line : lines) {
            for (Pattern pattern : patterns) {
                regexMatcher = pattern.matcher(line);
                if (regexMatcher != null) {
                    while (regexMatcher.find()) {
                        for (int i = 0; i < regexMatcher.groupCount(); i++) {
                            System.out.println(regexMatcher.group(i));
                            ++numbersFounded;
                        }
                    }
                }
            }
        }
        System.out.println(numbersFounded);
        return numbersFounded;
    }

    public int lematizacao(List<String> lines) {

        return 0;
    }
}
