package adobe_project;

// import com.sun.net.httpserver.HttpServer;
// import com.sun.net.httpserver.HttpHandler;
// import com.sun.net.httpserver.HttpExchange;

// import java.io.IOException;
// import java.io.OutputStream;
// import java.net.InetSocketAddress;


class RomanNumeralHTTPServer {


    


    public static String convertToRoman(int userInput) {
        if(userInput < 1 || userInput > 3999) {
            return "Please enter an integer in the interval [1, 3999].";
        }

        //store each digit value of user input
        Integer[] digitArr = new Integer[4];
        int mod = 10000;
        for(int i = 0; i < 4; i++) {
            digitArr[i] = (userInput % mod) / (mod / 10);
            mod = mod / 10;
        }

        String romanArr[][] = new String[][]{{"M", "M", "M"}, {"C", "D", "M"}, {"X", "L", "C"}, {"I", "V", "X"}};
        String romanNumeral = "";
        for(int i = 0; i < 4; i++) {
            //if digit is 4 or 9, need to append base value with 5x or 10x value above it
            if(digitArr[i] == 4) {
                romanNumeral += romanArr[i][0] + romanArr[i][1];
            }
            else if(digitArr[i] == 9) {
                romanNumeral += romanArr[i][0] + romanArr[i][2];
            }
            //if digit is 5 or more, add 5x value then append base values repeatedly as needed
            else if(digitArr[i] >= 5) {
                romanNumeral += romanArr[i][1];
                for(int j = 0; j < digitArr[i] % 5; j++) {
                    romanNumeral += romanArr[i][0];
                }
            }
            //default: repeat base value as needed (<= 3)
            else {
                for(int j = 0; j < digitArr[i]; j++) {
                    romanNumeral += romanArr[i][0];
                }
            }
        }
        return romanNumeral;

    }


}