package adobe_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import com.sun.net.httpserver.HttpServer;
import java.net.HttpURLConnection;
import java.net.URL;


@TestInstance(Lifecycle.PER_CLASS)
public class IntegrationTests {
    private HttpServer s;
    private int port = 8000;

    @BeforeAll
    public void startServer() {
        try {
            s = RomanNumeralHTTPServer.createServer(port);
            s.start();
        }
        catch (IOException e) {
            System.out.println("Test Server Failed To Start, IO Exception: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
        }

        //give time for server to start before pinging it with tests
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            System.out.println("Sleep failed");
        }

    }

    /*
     * Similar method of testing for all integration tests:
     * 1. have array of urls that need to be tested
     * 2. create connection with each url
     * 3. confirm expected response codes and message bodies
     * 4. If any IO Errors occur, fail the test and print a custom error message
     */


    @Test
    public void noQueryCurlTests() {
        String[] urls = new String[]{
            "http://localhost:" + String.valueOf(port) + "/romannumeral",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?"};
        for(String url_str : urls) {
            try {
                URL url = new URL(url_str);
                HttpURLConnection h = (HttpURLConnection) url.openConnection();
                h.setRequestMethod("GET");
                h.connect();
                assertEquals(h.getResponseCode(), 400);
                assertThrows(IOException.class, () -> h.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(h.getErrorStream()));
                String fullResponse = "";
                String responseString;
                while ((responseString = in.readLine()) != null) {
                    fullResponse += responseString;
                }
                in.close();
                assertEquals(fullResponse, "No query.");
            }
            catch (IOException e) {
                System.out.println("No Query Test for URL " + url_str + 
                    " Failed, IOException: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
                fail();
            }
        }
    }

    @Test
    public void invalidQueryCurlTests() {
        String[] urls = new String[]{
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=33=",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=33&query=44",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?not-query=33"};

        for(String url_str : urls) {
            try {
                URL url = new URL(url_str);
                HttpURLConnection h = (HttpURLConnection) url.openConnection();
                h.setRequestMethod("GET");
                h.connect();
                assertEquals(h.getResponseCode(), 400);
                assertThrows(IOException.class, () -> h.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(h.getErrorStream()));
                String fullResponse = "";
                String responseString;
                while ((responseString = in.readLine()) != null) {
                    fullResponse += responseString;
                }
                in.close();
                assertEquals(fullResponse, "Invalid queries.");
            }
            catch (IOException e) {
                System.out.println("Invalid Query Test for URL " + url_str + 
                    " Failed, IOException: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
                fail();
            }
        }
    }

    @Test
    public void malformedCurlTests() {
        String[] urls = new String[]{
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=abc",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=4000",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=0",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=-1",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=33a"};
        for(String url_str : urls) {
            try {
                URL url = new URL(url_str);
                HttpURLConnection h = (HttpURLConnection) url.openConnection();
                h.setRequestMethod("GET");
                h.connect();
                assertEquals(h.getResponseCode(), 400);
                assertThrows(IOException.class, () -> h.getInputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(h.getErrorStream()));
                String fullResponse = "";
                String responseString;
                while ((responseString = in.readLine()) != null) {
                    fullResponse += responseString;
                }
                in.close();
                assertEquals(fullResponse, "Please enter an integer in the interval [1, 3999].");
            }
            catch (IOException e) {
                System.out.println("Malformed Query Test for URL " + url_str + 
                " Failed, IOException: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));         
                fail();   
            }
        }
    }

    @Test
    public void JSONCurlTests() {
        String[] urls = new String[]{
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=1",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=40",
            "http://localhost:" + String.valueOf(port) + "/romannumeral?query=3999"};

        String[] jsons = new String[]{
            "{\"output\":\"I\",\"input\":\"1\"}",
            "{\"output\":\"XL\",\"input\":\"40\"}",
            "{\"output\":\"MMMCMXCIX\",\"input\":\"3999\"}"
        };
        for(int i = 0; i < urls.length; i++) {
            try {
                URL url = new URL(urls[i]);
                HttpURLConnection h = (HttpURLConnection) url.openConnection();
                h.setRequestMethod("GET");
                h.connect();
                assertEquals(h.getResponseCode(), 200);
                BufferedReader in = new BufferedReader(new InputStreamReader(h.getInputStream()));
                String fullResponse = "";
                String responseString;
                while ((responseString = in.readLine()) != null) {
                    fullResponse += responseString;
                }
                in.close();
                assertEquals(fullResponse, jsons[i]);
            }
            catch (IOException e) {
                System.out.println("Malformed Query Test for URL " + urls[i] + 
                " Failed, IOException: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));         
                fail();   
            }
        }
    }


    @AfterAll
    public void stopServer() {
        s.stop(0);
    }

}
