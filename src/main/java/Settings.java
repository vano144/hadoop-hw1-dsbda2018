import com.google.common.base.CharMatcher;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class Settings {

    // check is word contains only ascii
    public static boolean isAscii(String word) {
        return CharMatcher.ASCII.matchesAllOf(word);
    }

    // check word length
    public static boolean isValidLen(String word) {
        return word.length() >= MIN_WORD_LEN;
    }

    // validate word and increase suitable counters in case of bad result check
    public static boolean isSkipWord(String word, Context context) {
        boolean onlyAscii = Settings.isAscii(word);
        boolean isValidLen = Settings.isValidLen(word);
        if (!isValidLen) {
            context.getCounter(Settings.LONGEST_WORDS_COUNTERS.BAD_WORD_LEN).increment(1);
        }
        if (!onlyAscii) {
            context.getCounter(Settings.LONGEST_WORDS_COUNTERS.BAD_WORD_ASCII).increment(1);
            return true;
        }
        return false;
    }

    public static final String WORDS_DELIMITER = ":";
    public static final String CSV_DELIMITER = ":";
    private static final Integer MIN_WORD_LEN = 5;

    // counters to watch for amount of bad len and bad ascii words
    enum LONGEST_WORDS_COUNTERS {
        BAD_WORD_ASCII,
        BAD_WORD_LEN
    }
}
