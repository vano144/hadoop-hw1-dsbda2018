import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LongestWordsMapperTest {

    private MapDriver<Object, Text, Text, LongWritable> mapDriver;
    private String maxWord = "test";


    @Before
    public void setUp() {
        mapDriver = MapDriver.newMapDriver(new LongestWordsMapper());
    }


    // test mapper behaviour for one max word
    @Test
    public void CorrectOneLongestWordsMapperTest() throws IOException {

        mapDriver.withInput(
                new LongWritable(),
                new Text(maxWord)
        );
        mapDriver.withInput(
                new LongWritable(),
                new Text("gh")
        );
        mapDriver.withOutput(
                new Text(maxWord),
                new LongWritable(maxWord.length())
        );
        mapDriver.runTest();
    }

    // test mapper behaviour for multiple max word
    @Test
    public void CorrectMultipleLongestWordsMapperTest() throws IOException {
        String secondMaxWord = "tset";
        mapDriver.withInput(
                new LongWritable(),
                new Text(maxWord)
        );
        mapDriver.withInput(
                new LongWritable(),
                new Text(secondMaxWord)
        );
        mapDriver.withInput(
                new LongWritable(),
                new Text("lll")
        );
        mapDriver.withOutput(
                new Text(String.format("%s%s%s", maxWord, Settings.WORDS_DELIMITER, secondMaxWord)),
                new LongWritable(maxWord.length())
        );
        mapDriver.runTest();
    }

}
