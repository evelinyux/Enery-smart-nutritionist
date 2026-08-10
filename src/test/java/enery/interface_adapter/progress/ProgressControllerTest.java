package enery.interface_adapter.progress;

import enery.use_case.progress.ProgressInputBoundary;
import enery.use_case.progress.ProgressInputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProgressControllerTest {

    private ProgressInputBoundary interactor;
    private ProgressController controller;

    @BeforeEach
    void setUp() {
        interactor =
                mock(ProgressInputBoundary.class);

        controller =
                new ProgressController(
                        interactor
                );
    }

    @Test
    void executeCreatesCorrectInputDataTest() {
        controller.execute(
                "Ethan"
        );

        final ArgumentCaptor<ProgressInputData> captor =
                ArgumentCaptor.forClass(
                        ProgressInputData.class
                );

        verify(interactor)
                .execute(
                        captor.capture()
                );

        assertEquals(
                "Ethan",
                captor.getValue()
                        .getUsername()
        );
    }

    @Test
    void executePassesEmptyUsernameTest() {
        controller.execute(
                ""
        );

        final ArgumentCaptor<ProgressInputData> captor =
                ArgumentCaptor.forClass(
                        ProgressInputData.class
                );

        verify(interactor)
                .execute(
                        captor.capture()
                );

        assertEquals(
                "",
                captor.getValue()
                        .getUsername()
        );
    }

    @Test
    void executePassesNullUsernameTest() {
        controller.execute(
                null
        );

        final ArgumentCaptor<ProgressInputData> captor =
                ArgumentCaptor.forClass(
                        ProgressInputData.class
                );

        verify(interactor)
                .execute(
                        captor.capture()
                );

        assertEquals(
                null,
                captor.getValue()
                        .getUsername()
        );
    }
}
