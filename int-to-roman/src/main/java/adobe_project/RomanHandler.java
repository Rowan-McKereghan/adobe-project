package adobe_project;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


class RomanHandler implements HttpHandler {

        public String parseQueryStringAndReturnResponseString(String query) {
            if(query == null) {
                return "No query.";
            }
            String[] queriedNum = query.split("=");
            if(queriedNum.length != 2 || !queriedNum[0].equals("query")) {
                return "Invalid queries.";
            }
            try {
                int num = Integer.valueOf(queriedNum[1]);
                return RomanNumeralHTTPServer.convertToRoman(num);
            }
            catch (NumberFormatException e) {
                return "Please enter an integer in the interval [1, 3999].";
            }
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = parseQueryStringAndReturnResponseString(exchange.getRequestURI().getQuery());
            response += "\n";
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        }

    }