import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;


public class LongestWordsReducer
        extends Reducer<Text,LongWritable,Text,LongWritable> {

    private long maxLength = 0;
    private HashSet<String> words;

    public void reduce(Text key, Iterable<LongWritable> values,
                       Context context
    ){
        HashSet<String> new_words;
        for (LongWritable val : values) {
            long length = val.get();
            // expand set of longest words
            if (maxLength < length) {
                words = Settings.convertStrToSet(key.toString());
                maxLength = length;
            } else if (maxLength == length) {
                new_words = Settings.convertStrToSet(key.toString());
                words.addAll(new_words);

            }
        }
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        // write longest words and its length
        context.write(new Text(Settings.convertSetToStr(words)), new LongWritable(maxLength));
    }
}

