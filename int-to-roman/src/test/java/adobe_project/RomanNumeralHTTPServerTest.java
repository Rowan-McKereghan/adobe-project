package adobe_project;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class RomanNumeralHTTPServerTest {

    @Test
    public void oneToRomanTest() {
        assertEquals(RomanNumeralHTTPServer.convertToRoman(1), "I");
    }

    @Test
    public void romanEdgeCaseTests() {
        //check four logic
        assertEquals(RomanNumeralHTTPServer.convertToRoman(4), "IV");
        //check nine logic
        assertEquals(RomanNumeralHTTPServer.convertToRoman(90), "XC");
        //check >= 5 logic
        assertEquals(RomanNumeralHTTPServer.convertToRoman(800), "DCCC");
        //check <= 3 logic
        assertEquals(RomanNumeralHTTPServer.convertToRoman(1233), "MCCXXXIII");
        //last value
        assertEquals(RomanNumeralHTTPServer.convertToRoman(3999), "MMMCMXCIX");

    }

    @Test
    public void assortedNumsToRomanTests() { 

        assertEquals(RomanNumeralHTTPServer.convertToRoman(2756), "MMDCCLVI");
        assertEquals(RomanNumeralHTTPServer.convertToRoman(1074), "MLXXIV");
        assertEquals(RomanNumeralHTTPServer.convertToRoman(54), "LIV");

    }



    @Test
    public void userInputOutsideIntervalTests() {
        assertEquals(RomanNumeralHTTPServer.convertToRoman(4000), "Please enter an integer in the interval [1, 3999].");
        assertEquals(RomanNumeralHTTPServer.convertToRoman(0), "Please enter an integer in the interval [1, 3999].");
        assertEquals(RomanNumeralHTTPServer.convertToRoman(-1), "Please enter an integer in the interval [1, 3999].");
    }


}
