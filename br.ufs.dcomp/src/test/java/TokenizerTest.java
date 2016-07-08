import lab1.Token;
import lab1.Tokenizer;
import lab1.util.FileManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericm on 08-Jul-16.
 */
public class TokenizerTest {

    @Test
    public void testTokenizer() throws IOException {
        List<String> lines = new ArrayList<String>();
        lines.add("palavra1    palavra2  ! palavra3 ,., palavra4");
        lines.add("palavra1    palavra2  ! palavra3 ,., palavra4 palavra4 palavra4");

        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.linesToToken(lines);
        Assert.assertTrue(tokens!=null && !tokens.isEmpty());
        Assert.assertTrue(tokens.size()==4);
        tokens.forEach(token -> {
            if(token.getText().equals("palavra4"))
                Assert.assertTrue(token.getCounter()==4);
            if(token.getText().equals("palavra1"))
                Assert.assertTrue(token.getCounter()==2);
        });
    }

}
