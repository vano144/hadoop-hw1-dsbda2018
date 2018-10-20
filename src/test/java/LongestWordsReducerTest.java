import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LongestWordsReducerTest {
    private ReduceDriver<Text, LongWritable,Text,LongWritable> reduceDriver;
    private List<LongWritable> lengths;
    private List<LongWritable> lowerLengths;
    private String maxWord = "test";
    private HashSet<String> maxSet;

    @Before
    public void setUp() {
        reduceDriver = ReduceDriver.newReduceDriver(new LongestWordsReducer());
        lengths = new ArrayList<>();
        lowerLengths = new ArrayList<>();
        maxSet = new HashSet<>();
        maxSet.add(maxWord);
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

    // test reducer behaviour for multiple max set with repeats
    @Test
    public void CorrectMultipleWithRepeatsLongestWordsReducerTest() throws IOException {
        lengths.add(new LongWritable(maxWord.length()));
        lowerLengths.add(new LongWritable(3));
        String maxWordStrSet1 = String.format("%s%s%s", maxWord, Settings.WORDS_DELIMITER, "ffff");
        String maxWordStrSet2 = "zzzz";
        HashSet<String> maxWordSet1 = Settings.convertStrToSet(maxWordStrSet1);
        HashSet<String> maxWordSet2 = Settings.convertStrToSet(maxWordStrSet2);
        maxWordSet1.addAll(maxWordSet2);
        reduceDriver
                .withInput(
                        new Text(maxWordStrSet1),
                        lengths
                );
        // add repeat here and set should save us
        reduceDriver
                .withInput(
                        new Text(maxWordStrSet1),
                        lengths
                );
        reduceDriver
                .withInput(
                        new Text(maxWordStrSet2),
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
        reduceDriver.withOutput(new Text(Settings.convertSetToStr(maxWordSet1)), new LongWritable(maxWord.length()));
        reduceDriver.runTest();
    }
}
