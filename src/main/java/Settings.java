import com.google.common.base.CharMatcher;
import org.apache.hadoop.mapreduce.Mapper.Context;

import java.util.HashSet;

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


    // convert String Set
    public static HashSet<String> convertStrToSet(String words) {
        HashSet<String> result = new HashSet<>();
        for (String value: words.split(WORDS_DELIMITER)) {
            result.add(value);
        }
        return result;
    }

    // convert Set to String
    public static String convertSetToStr(HashSet<String> words) {
        return String.join(Settings.WORDS_DELIMITER, words);
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
