import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

public class LongestWords {

    private static final Logger LOG = Logger.getLogger(LongestWords.class.getName());

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        // set for csv
        conf.set("mapreduce.output.textoutputformat.separator", Settings.CSV_DELIMITER);

        // initialize job
        Job job = Job.getInstance(conf, "longest word[s]");
        job.setJarByClass(LongestWords.class);
        // set LongestWords for map reduce work
        job.setMapperClass(LongestWordsMapper.class);
        job.setReducerClass(LongestWordsReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setSortComparatorClass(LongWritable.DecreasingComparator.class);
        // init for input, output
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        int status = job.waitForCompletion(true) ? 0 : 1;
        // show counters for ba ascii and bad length
        LOG.info(String.format("Numbers of bad ascii words = %d",
                job.getCounters().findCounter(Settings.LONGEST_WORDS_COUNTERS.BAD_WORD_ASCII).getValue()));
        LOG.info(String.format("Numbers of bad len words = %d",
                job.getCounters().findCounter(Settings.LONGEST_WORDS_COUNTERS.BAD_WORD_LEN).getValue()));
        System.exit(status);
    }
}