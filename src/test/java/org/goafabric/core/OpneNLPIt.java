package org.goafabric.core;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class OpneNLPIt {

    @Test
    public void test() throws IOException {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        //var text = "John is 26 years old. His best friend's name is Leonard. He has a sister named Penny.";
        var text = "The story of Sheldon Cooper and his boss Monty Burns who lives";

        String[] tokens = tokenizer.tokenize(text);

        InputStream inputStreamNameFinder = getClass()
                .getResourceAsStream("/models/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(
                inputStreamNameFinder);
        NameFinderME nameFinderME = new NameFinderME(model);
        List<Span> spans = Arrays.asList(nameFinderME.find(tokens));
        //spans.stream().forEach(span -> span.);
        spans.forEach(span -> System.out.println(tokens[span.getStart()] + " " + tokens[span.getEnd() -1]));
    }
}
