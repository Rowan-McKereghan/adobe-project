package adobe_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.json.JSONObject;

public class RomanHandlerTest {

    //for testing buffer overflow avoidance
    public static final String superLongString = """
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    zZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2VzZnMnsydzZTNh82dzYVhzY/Nk82UzLnMvMyjbMy0zZTMsMykzJ/NlOG4vcyrLs2V
    """;

    @Test
    public void noQueryTest() {
        RomanHandler test = new RomanHandler();
        HttpResponse httpTest = new HttpResponse(400, "No query.", "text/plain");
        assertEquals(test.parseQueryStringAndReturnResponseString(null), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString(""), httpTest);
    }

    @Test
    public void invalidQueryTests() {
        RomanHandler test = new RomanHandler();
        HttpResponse httpTest = new HttpResponse(400, "Invalid queries.", "text/plain");
        String[] queries = new String[]{"query", "query=", "blahblah", "notquery=400", superLongString + "=400", "query=" + superLongString};
        for(String query : queries) {
            assertEquals(test.parseQueryStringAndReturnResponseString(query), httpTest);
        }
    }

    @Test
    public void invalidQueryValuesTests() {
        RomanHandler test = new RomanHandler();
        HttpResponse httpTest = new HttpResponse(400, "Please enter an integer in the interval [1, 3999].", "text/plain");
        String[] queries = new String[]{"query=abc", "query=400a", "query=4000", "query=0", "query=-1"};
        for(String query : queries) {
            assertEquals(test.parseQueryStringAndReturnResponseString(query), httpTest);
        }
    }

    @Test
    public void validQueryTests() {
        String[] inputs = new String[]{"2756", "1074", "54"};
        String[] outputs = new String[]{"MMDCCLVI", "MLXXIV", "LIV"};
        RomanHandler test = new RomanHandler();
        JSONObject testJSON = new JSONObject();
        for(int i = 0; i < inputs.length; i++) {
            testJSON.put("output", outputs[i]);
            testJSON.put("input", inputs[i]);
            HttpResponse httpTest = test.parseQueryStringAndReturnResponseString("query=" + inputs[i]);
            assertEquals(httpTest.getCode(), 200);
            assertEquals(httpTest.getType(), "application/json");
            JSONObject httpJSON = new JSONObject(httpTest.getResponse());
            assertTrue(httpJSON.has("output"));
            assertTrue(httpJSON.has("input"));
            assertEquals(testJSON.getString("output"), httpJSON.getString("output"));
            assertEquals(testJSON.getString("input"), httpJSON.getString("input"));
            testJSON.clear();
        }
    }

    
}
