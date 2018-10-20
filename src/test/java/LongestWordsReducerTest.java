import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LongestWordsReducerTest {
    private ReduceDriver<Text, LongWritable,Text,LongWritable> reduceDriver;
    private List<LongWritable> lengths;
    private List<LongWritable> lowerLengths;
    private String maxWord = "test";

    @Before
    public void setUp() {
        reduceDriver = ReduceDriver.newReduceDriver(new LongestWordsReducer());
        lengths = new ArrayList<>();
        lowerLengths = new ArrayList<>();

    }

    // test reducer behaviour for one max set
    @Test
    public void CorrectOneLongestWordsReducerTest() throws IOException {
        lengths.add(new LongWritable(maxWord.length()));
        lowerLengths.add(new LongWritable(3));
        reduceDriver
                .withInput(
                        new Text(maxWord),
                        lengths
                );
        reduceDriver
                .withInput(
                        new Text("tes"),
                        lowerLengths
                );
        reduceDriver.withOutput(new Text(maxWord), new LongWritable(maxWord.length()));
        reduceDriver.runTest();
    }

    // test reducer behaviour for multiple max set
    @Test
    public void CorrectMultipleLongestWordsReducerTest() throws IOException {
        lengths.add(new LongWritable(maxWord.length()));
        lowerLengths.add(new LongWritable(3));
        String maxWordSet1 = String.format("%s%s%s", maxWord, Settings.WORDS_DELIMITER, "ffff");
        String maxWordSet2 = "zzzz";
        String expected = String.format("%s%s%s", maxWordSet1, Settings.WORDS_DELIMITER,maxWordSet2);
        reduceDriver
                .withInput(
                        new Text(maxWordSet1),
                        lengths
                );
        reduceDriver
                .withInput(
                        new Text(maxWordSet2),
                        lengths
                );
        reduceDriver
                .withInput(
                        new Text("zzz:fff:dss"),
                        lowerLengths
                );
        reduceDriver
                .withInput(
                        new Text("zzz"),
                        lowerLengths
                );
        reduceDriver.withOutput(new Text(expected), new LongWritable(maxWord.length()));
        reduceDriver.runTest();
    }
}
