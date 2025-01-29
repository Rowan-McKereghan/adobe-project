package adobe_project;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;


public class Main {
    public static void main(String[] args) {
        try {
            HttpServer server = RomanNumeralHTTPServer.createServer(8080);
            server.start();
        }
        catch (IOException e) {
            System.out.println("Server Failed, IO Exception: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
        }
    }
}