package enery.interface_adapter.tracking;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import enery.use_case.tracking.TrackingInputBoundary;
import enery.use_case.tracking.TrackingInputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackingControllerTest {

    @Test
    void passesInputToInteractor() {
        final TrackingInputData[] received = {null};

        final TrackingInputBoundary interactor =
                inputData -> received[0] = inputData;

        final TrackingController controller =
                new TrackingController(interactor);

        final LocalDate date =
                LocalDate.of(2026, 7, 26);

        controller.execute("Paul", date);

        assertEquals(
                "Paul",
                received[0].getUsername());
        assertEquals(
                date,
                received[0].getDate());
    }
}
