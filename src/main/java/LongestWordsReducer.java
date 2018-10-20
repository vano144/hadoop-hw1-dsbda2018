import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class LongestWordsReducer
        extends Reducer<Text,LongWritable,Text,LongWritable> {

    private long maxLength = 0;
    private String words = "";

    public void reduce(Text key, Iterable<LongWritable> values,
                       Context context
    ){
        for (LongWritable val : values) {
            long length = val.get();
            // expand set of longest words
            if (maxLength < length) {
                words = key.toString();
                maxLength = length;
            } else if (maxLength == length) {
                words += Settings.WORDS_DELIMITER + key.toString();
            }
        }
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        // write longest words and its length
        context.write(new Text(words), new LongWritable(maxLength));
    }
}

