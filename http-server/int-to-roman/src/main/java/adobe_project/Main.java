package adobe_project;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            logger.info("Creating and Binding Server...");
            HttpServer server = RomanNumeralHTTPServer.createServer(8080, logger);
            logger.info("Bind Sucessful, Starting Server at port 8080");
            server.start();
        }
        catch (IOException e) {
            logger.fatal("Server Failed, IO Exception: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
        }
    }
}