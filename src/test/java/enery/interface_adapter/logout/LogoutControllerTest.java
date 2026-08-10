package enery.interface_adapter.logout;

import enery.use_case.logout.LogoutInputBoundary;
import enery.use_case.logout.LogoutInputData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LogoutControllerTest {

    @Test
    void executeTest() {
        final LogoutInputBoundary inputBoundary =
                mock(LogoutInputBoundary.class);

        final LogoutController controller =
                new LogoutController(inputBoundary);

        controller.execute("Nicole");

        final ArgumentCaptor<LogoutInputData> captor =
                ArgumentCaptor.forClass(LogoutInputData.class);

        verify(inputBoundary).execute(captor.capture());

        final LogoutInputData inputData = captor.getValue();

        assertEquals("Nicole", inputData.getUsername());
    }
}