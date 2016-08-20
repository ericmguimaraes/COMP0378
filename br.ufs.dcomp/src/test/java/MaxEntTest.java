import opennlp.model.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import opennlp.maxent.GIS;

public class MaxEntTest {

    static Event createEvent(String outcome, String... context) {
        return new Event(outcome, context);
    }

    @Test
    public void main() throws IOException {

        // here are the input training samples
        List<Event> samples =  Arrays.asList(new Event[] {
                //           outcome + context
                createEvent("review positiva", "aracaju=1", "feliz=1"),
                createEvent("review negativa", "natal=1", "verde=0"),
                createEvent("review positiva", "marte=5", "roxo=6"),
                createEvent("review negativa", "cinza=0", "jupter=0")
        });

        // training the model
        EventStream stream = new ListEventStream(samples);
        MaxentModel model = GIS.trainModel(stream);

        // using the trained model to predict the outcome given the context
        String[] context = {"marte=6", "jupter=0"};
        double[] outcomeProbs = model.eval(context);
        String outcome = model.getAllOutcomes(outcomeProbs);

        System.out.println(outcome); // output: c=1

        // using the trained model to predict the outcome given the context
        context = new String[]{"aracaju=1", "verde=3"};
        outcomeProbs = model.eval(context);
        outcome = model.getBestOutcome(outcomeProbs);

        System.out.println(outcome); // output: c=1
    }

}
