package exercise1;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// External API dependency
interface ExternalApi {
    String getData();
}

// Service under test
class MyService {
    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return this.externalApi.getData();
    }
}

// Test Class (package-private to allow creation inside a file named with spaces)
class Exercise1MockingAndStubbingTest {

    @Test
    void testExternalApi() {
        // 1. Create a mock object for the external API.
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        
        // 2. Stub the methods to return predefined values.
        when(mockApi.getData()).thenReturn("Mock Data");
        
        // 3. Write a test case that uses the mock object.
        MyService service = new MyService(mockApi);
        String result = service.fetchData();
        
        assertEquals("Mock Data", result);
    }
}
