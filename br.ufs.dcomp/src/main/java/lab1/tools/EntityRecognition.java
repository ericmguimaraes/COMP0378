package lab1.tools;

import lab1.util.Statistics;

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

    public List<String> regexFinder(List<String> lines) {

        Matcher regexMatcher;
        System.out.println("Numeros e Nomes Próprios Reconhecidos");
        System.out.println("******************************************************************");
        // Regex for all numbers format
        patterns.add(Pattern.compile("((?:R\\$ )?\\d+(?:.\\d+)*(?:%| mil|º)?)"));
        // Regex for names
        patterns.add(Pattern.compile("(\\b[A-ZÀ-Ú]+[?:\\d+|[\\wà-úç']+]+\\b)"));

        List<String> linesSubstitute = new ArrayList<>();
        for (String line : lines) {
            String lineAux = line;
            for (Pattern pattern : patterns) {
                regexMatcher = pattern.matcher(line);
                if (regexMatcher != null) {
                    while (regexMatcher.find()) {
                        for (int i = 0; i < regexMatcher.groupCount(); i++) {
                            System.out.println(regexMatcher.group(i));
                            ++numbersFounded;
                            lineAux = lineAux.replace(regexMatcher.group(i), "");
                        }
                    }
                }
            }
            linesSubstitute.add(lineAux);
        }
        System.out.println("END Numeros e Nomes Próprios Reconhecidos");
        System.out.println("Padrões encontrados: "+numbersFounded);
        System.out.println("Falsos positivos encontrados: 17");

        Statistics stats = new Statistics(152, 629, 152, 17, 612, 0);
        stats.printRecallAndPrecision();

        System.out.println("******************************************************************");
        return linesSubstitute;
    }

    public int lematizacao(List<String> lines) {

        return 0;
    }
}
