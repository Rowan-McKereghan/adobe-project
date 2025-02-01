package adobe_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.HttpServer;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IntegrationTests {

    private HttpServer s;
    private int port = 8000;

    @Mock
    Logger mockLogger;
    @Mock
    PrometheusMeterRegistry mockRegistry;
    @Mock
    Counter mockCounter;

    

    private static final Logger logger = LogManager.getLogger(IntegrationTests.class);


    @BeforeAll
    public void startServer() {
        MockitoAnnotations.openMocks(this);
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        when(mockLogger.isErrorEnabled()).thenReturn(true);
        when(mockLogger.isFatalEnabled()).thenReturn(true);
        when(mockRegistry.scrape()).thenReturn("someRegData");
        try {
            s = RomanNumeralHTTPServer.createServer(port, mockLogger, mockCounter, mockRegistry);
            s.start();
        }
        catch (IOException e) {
            logger.fatal("Test Server Failed To Start, IO Exception: " + (e.getMessage() == null ? "Null Message" : e.getMessage()));
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
                logger.fatal("No Query Test for URL " + url_str + 
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
                logger.fatal("Invalid Query Test for URL " + url_str + 
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
                logger.fatal("Malformed Query Test for URL " + url_str + 
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

        JSONObject[] jsons = new JSONObject[]{
            new JSONObject("{\"output\":\"I\",\"input\":\"1\"}"),
            new JSONObject("{\"output\":\"XL\",\"input\":\"40\"}"),
            new JSONObject("{\"output\":\"MMMCMXCIX\",\"input\":\"3999\"}")
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
                JSONObject testJSON = new JSONObject(fullResponse);
                assertTrue(testJSON.has("output"));
                assertTrue(testJSON.has("input"));
                assertEquals(testJSON.getString("output"), jsons[i].getString("output"));
                assertEquals(testJSON.getString("input"), jsons[i].getString("input"));

            }
            catch (IOException e) {
                logger.fatal("JSON 200 Query Test for URL " + urls[i] + 
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
