import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class SettingsTest {


    // test for check  isascii function for bad and good case
    @Test
    public void isAsciiTest() {
        assertEquals(true, Settings.isAscii("test"));
        assertFalse(Settings.isAscii("uơ&ƝƧuǝǯ8ǄjƢǅƱƹUKǕưǘǋǅǀx"));
    }


    // test for check isValidLen function for bad and good case
    @Test
    public void isValidLenTest() {
        assertEquals(true, Settings.isValidLen("testtest"));
        assertFalse(Settings.isValidLen("q"));
    }

    // test for convert from Str to Set
    @Test
    public void convertStrToSetTest() {
        String first = "test";
        String second = "second";
        HashSet<String> data = Settings.convertStrToSet(String.format("%s%s%s", first,
                Settings.WORDS_DELIMITER, second));
        assertTrue(data.contains(first));
        assertTrue(data.contains(second));
    }

    // test for convert from Set to Str
    @Test
    public void convertSetToStrTest() {
        String first = "test";
        String second = "second";
        HashSet<String> data = new HashSet<>();
        data.add(first);
        data.add(second);
        assertEquals(Settings.convertSetToStr(data), String.format("%s%s%s", first,
                Settings.WORDS_DELIMITER, second));
    }

}

