package adobe_project;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import org.apache.logging.log4j.Logger;
import io.micrometer.core.instrument.Counter;


class RomanHandler implements HttpHandler {

    private final Logger logger;
    private Counter reqCounter;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public RomanHandler(Logger mainLogger, Counter requestCounter) {
        this.logger = mainLogger;
        this.reqCounter = requestCounter;
    }

    public HttpResponse parseQueryStringAndReturnResponseString(String query) {
        //Test for no query in URL request
        if(query == null || query.length() == 0) {
            logger.error("No query in URL request. Serving 400.");
            return new HttpResponse(400, "No query.", "text/plain");
        }
        //edge case where query has '=' at end of query string, so split() will not add a new element
        if(query.charAt(query.length() - 1) == '=') {
            logger.error("Invalid queries in URL request. Serving 400.");
            return new HttpResponse(400, "Invalid queries.", "text/plain");
        }
        //check that there is only 1 query and check for buffer overflow
        String[] queriedNum = query.split("=");
        if(queriedNum.length != 2 || queriedNum[0].length() > 1024 || queriedNum[1].length() > 1024) {
            logger.error("Invalid queries in URL request. Serving 400.");
            return new HttpResponse(400, "Invalid queries.", "text/plain");
        }
        //check that the query is named 'query'
        if(!queriedNum[0].equals("query")) {
            logger.error("Invalid queries in URL request. Serving 400.");
            return new HttpResponse(400, "Invalid queries.", "text/plain");
        }
        //attempt to return JSON object if queried number is in [1, 3999]. Else serve 400.
        try {
            int num = Integer.valueOf(queriedNum[1]);
            String roman = RomanNumeralHTTPServer.convertToRoman(num);
            JSONObject obj = new JSONObject().put("output", roman);
            obj.put("input", queriedNum[1]);
            return new HttpResponse(200, obj.toString(), "application/json");
        }
        catch (NumberFormatException e) {
            logger.error("User input in query must be integer 1 <= n <= 3999, serving 400.");
            return new HttpResponse(400, "Please enter an integer in the interval [1, 3999].", "text/plain");
        }
    }

    @Override
    public void handle(HttpExchange exchange) {
        reqCounter.increment();
        HttpResponse httpRes = parseQueryStringAndReturnResponseString(exchange.getRequestURI().getQuery());

        exchange.getResponseHeaders().set("Content-Type", httpRes.getType());
        exchange.getResponseHeaders().set("Charset", "UTF-8");

        //allow requests from frontend app
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        try {
            logger.info("Writing HTTP Headers");
            exchange.sendResponseHeaders(httpRes.getCode(), httpRes.getResponse().getBytes(UTF_8).length);
            logger.info("HTTP Headers sent, writing response body");
            OutputStream os = exchange.getResponseBody();
            os.write(httpRes.getResponse().getBytes(UTF_8));
            os.flush();
            logger.info("HTTP Response sent, closing write stream");
            os.close();
        }
        catch (IOException e) {
            logger.error("HTTP Write Stream Error, " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
        }

    }

}