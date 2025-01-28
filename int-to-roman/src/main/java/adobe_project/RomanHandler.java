package adobe_project;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;


class RomanHandler implements HttpHandler {

        private static final Charset UTF_8 = StandardCharsets.UTF_8;

        public HttpResponse parseQueryStringAndReturnResponseString(String query) {
            if(query == null) {
                return new HttpResponse(400, "No query.");
            }
            String[] queriedNum = query.split("=");
            if(queriedNum.length != 2 || queriedNum[0].length() > 1024 || queriedNum[1].length() > 1024) {
                return new HttpResponse(400, "Invalid queries.");
            }
            if(!queriedNum[0].equals("query")) {
                return new HttpResponse(400, "Invalid queries.");
            }
            try {
                int num = Integer.valueOf(queriedNum[1]);
                String roman = RomanNumeralHTTPServer.convertToRoman(num);
                JSONObject obj = new JSONObject().put("output", roman);
                obj.put("input", queriedNum[1]);
                return new HttpResponse(200, obj.toString());
            }
            catch (NumberFormatException e) {
                return new HttpResponse(400, "Please enter an integer in the interval [1, 3999].");
            }
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            HttpResponse httpRes = parseQueryStringAndReturnResponseString(exchange.getRequestURI().getQuery());
            exchange.sendResponseHeaders(httpRes.getCode(), httpRes.getResponse().getBytes(UTF_8).length);
            OutputStream os = exchange.getResponseBody();
            os.write(httpRes.getResponse().getBytes(UTF_8));
            os.close();
        }

    }