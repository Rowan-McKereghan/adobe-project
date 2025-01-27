package adobe_project;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            RomanNumeralHTTPServer.createAndStartServer();
        }
        catch (IOException e) {
            System.out.println("IO error");
        }
    }
}