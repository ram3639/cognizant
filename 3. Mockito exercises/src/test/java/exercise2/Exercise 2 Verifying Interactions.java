package exercise2;

import static org.mockito.Mockito.*;
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
class Exercise2VerifyingInteractionsTest {

    @Test
    void testVerifyInteraction() {
        // 1. Create a mock object.
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        
        // 2. Call the method (via the service).
        MyService service = new MyService(mockApi);
        service.fetchData();
        
        // 3. Verify the interaction.
        verify(mockApi).getData();
    }
}
