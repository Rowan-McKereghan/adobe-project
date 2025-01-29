package adobe_project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class HTTPResponseTest {

    @Test
    public void equalityTests() {
        assertEquals(new HttpResponse(200, "Test Str", "Test Type"), new HttpResponse(200, "Test Str", "Test Type"));
    }

    @Test
    public void hashTest() {
        assertEquals(new HttpResponse(200, "Test Str", "Test Type").hashCode(), new HttpResponse(200, "Test Str", "Test Type").hashCode());
    }

}