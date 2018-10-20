import org.junit.Test;

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

}

