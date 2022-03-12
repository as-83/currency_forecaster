package edu.abdsul.forecaster;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class GraphResultFormatterTest {
    @Test
    public void graphResultFormatterTest() {
        GraphResultFormatter graphResultFormatter = new GraphResultFormatter();

        try {
            graphResultFormatter.drawPicture();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }

    }

}
