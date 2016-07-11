
import lab1.model.Token;
import lab1.tools.Tokenizer;
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
        String text = "palavra1    palavra2  ! palavra3 ,., palavra4 palavra1    palavra2  ! palavra3 ,., palavra4 palavra4 palavra4";

        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.textToTokens(text);
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
