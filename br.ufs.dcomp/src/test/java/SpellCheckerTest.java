import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import lab1.tools.SpellChecker;

/**
 * Created by Ariel on 10-Jul-16.
 */
public class SpellCheckerTest {

    @Test
    public void main() {
        SpellChecker spellChecker = new SpellChecker();
        List<String> v = new ArrayList<String>();
        v.add("ariel");
        v.add("ari");
        v.add("joao");
        String teste = spellChecker.SpellChecker("krie", v);
    }
}
