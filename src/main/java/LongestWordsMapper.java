import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;


public class LongestWordsMapper
        extends Mapper<Object, Text, Text, LongWritable> {

    private int maxLength = 0;
    private HashSet<String> words = new HashSet<>();

    public void map(Object key, Text value, Context context
    ) {
        StringTokenizer itr = new StringTokenizer(value.toString());
        while (itr.hasMoreTokens()) {
            String word = itr.nextToken();
            if (Settings.isSkipWord(word, context))
                continue;

            // collect only the longest words in line
            if (maxLength < word.length()) {
                words.clear();
                maxLength = word.length();
                words.add(word);
            } else if (maxLength == word.length()) {
                words.add(word);
            }
        }
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        // write only the longest words in line
        String result = String.join(Settings.WORDS_DELIMITER, words);
        context.write(new Text(result), new LongWritable(maxLength));
    }
}
