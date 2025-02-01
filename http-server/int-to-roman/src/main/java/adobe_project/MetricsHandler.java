package adobe_project;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.apache.logging.log4j.Logger;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;


public class MetricsHandler implements HttpHandler {

    private final Logger logger;
    private final PrometheusMeterRegistry registry;
    

    public MetricsHandler(Logger mainLogger, PrometheusMeterRegistry mainRegistry) {
        this.logger = mainLogger;
        this.registry = mainRegistry;
    }

    @Override
    public void handle(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        try {
            logger.info("Writing Metrics Headers");
            exchange.sendResponseHeaders(200, 0);
            logger.info("Writing Metrics Data");
            OutputStream os = exchange.getResponseBody();
            os.write(registry.scrape().getBytes());
            os.flush();
            os.close();
        }
        catch(IOException e) {
            logger.error("Failed to write metrics, " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
        }

    }
        
    
}
