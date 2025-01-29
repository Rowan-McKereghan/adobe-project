package adobe_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

public class RomanHandlerTest {

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
        assertEquals(test.parseQueryStringAndReturnResponseString("query"), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("query="), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("blahblah"), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("notquery=400"), httpTest);
        //test for string buffer overflow
        assertEquals(test.parseQueryStringAndReturnResponseString(superLongString + "=400"), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("query=" + superLongString), httpTest);
    }

    @Test
    public void invalidQueryValuesTests() {
        RomanHandler test = new RomanHandler();
        HttpResponse httpTest = new HttpResponse(400, "Please enter an integer in the interval [1, 3999].", "text/plain");
        assertEquals(test.parseQueryStringAndReturnResponseString("query=abc"), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("query=400a"), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("query=4000"), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("query=0"), httpTest);
        assertEquals(test.parseQueryStringAndReturnResponseString("query=-1"), httpTest);
    }

    @Test
    public void validQueryTests() {
        RomanHandler test = new RomanHandler();
        JSONObject obj = new JSONObject();
        obj.put("output", "MMDCCLVI");
        obj.put("input", "2756");
        assertEquals(test.parseQueryStringAndReturnResponseString("query=2756"), new HttpResponse(200, obj.toString(), "application/json"));
        obj.clear();
        obj.put("output", "MLXXIV");
        obj.put("input", "1074");
        assertEquals(test.parseQueryStringAndReturnResponseString("query=1074"), new HttpResponse(200, obj.toString(), "application/json"));
        obj.clear();
        obj.put("output", "LIV");
        obj.put("input", "54");
        assertEquals(test.parseQueryStringAndReturnResponseString("query=54"), new HttpResponse(200, obj.toString(), "application/json"));
    }

    
}
