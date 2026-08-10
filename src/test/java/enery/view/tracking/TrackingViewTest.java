package enery.view.tracking;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import enery.interface_adapter.change_password.LoggedInState;
import enery.interface_adapter.change_password.LoggedInViewModel;
import enery.interface_adapter.shared.ViewManagerModel;
import enery.interface_adapter.tracking.TrackingController;
import enery.interface_adapter.tracking.TrackingState;
import enery.interface_adapter.tracking.TrackingViewModel;
import enery.use_case.tracking.DailyTrackingSummary;
import enery.use_case.tracking.TrackingPeriod;
import enery.view.home.HomeView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrackingViewTest {

    private TrackingViewModel trackingViewModel;
    private ViewManagerModel viewManagerModel;
    private LoggedInViewModel loggedInViewModel;

    private TrackingState trackingState;
    private LoggedInState loggedInState;

    private TrackingView view;

    @BeforeEach
    void setUp() throws Exception {
        trackingViewModel =
                mock(TrackingViewModel.class);

        viewManagerModel =
                mock(ViewManagerModel.class);

        loggedInViewModel =
                mock(LoggedInViewModel.class);

        trackingState =
                mock(TrackingState.class);

        loggedInState =
                mock(LoggedInState.class);

        when(trackingViewModel.getState())
                .thenReturn(trackingState);

        when(loggedInViewModel.getState())
                .thenReturn(loggedInState);

        when(trackingState.getErrorMessage())
                .thenReturn("");

        when(trackingState.getDailySummaries())
                .thenReturn(List.of());

        SwingUtilities.invokeAndWait(
                () -> view =
                        new TrackingView(
                                trackingViewModel,
                                viewManagerModel,
                                loggedInViewModel
                        )
        );
    }

    @Test
    void getViewNameReturnsTracking() {
        assertEquals(
                TrackingView.VIEW_NAME,
                view.getViewName()
        );
    }

    @Test
    void tableIsNotEditable() {
        final JTable table =
                findComponent(
                        view,
                        JTable.class
                );

        assertFalse(
                table.getModel()
                        .isCellEditable(
                                0,
                                0
                        )
        );
    }

    @Test
    void loadWithoutControllerShowsError()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Load Summary"
                ).doClick()
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Tracking controller is not configured."
                )
        );
    }

    @Test
    void invalidDateShowsValidationError()
            throws Exception {

        final TrackingController controller =
                mock(TrackingController.class);

        view.setTrackingController(
                controller
        );

        final JTextField dateField =
                findComponent(
                        view,
                        JTextField.class
                );

        SwingUtilities.invokeAndWait(() -> {
            dateField.setText(
                    "not-a-date"
            );

            findButton(
                    view,
                    "Load Summary"
            ).doClick();
        });

        assertTrue(
                containsLabelText(
                        view,
                        "Enter the date using YYYY-MM-DD."
                )
        );
    }

    @Test
    void validDateCallsController()
            throws Exception {

        final TrackingController controller =
                mock(TrackingController.class);

        view.setTrackingController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        final JTextField dateField =
                findComponent(
                        view,
                        JTextField.class
                );

        final JComboBox<?> periodBox =
                findComponent(
                        view,
                        JComboBox.class
                );

        final LocalDate date =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        SwingUtilities.invokeAndWait(() -> {
            dateField.setText(
                    date.toString()
            );

            findButton(
                    view,
                    "Load Summary"
            ).doClick();
        });

        verify(controller)
                .execute(
                        eq("ethan"),
                        eq(date),
                        eq(
                                (TrackingPeriod)
                                        periodBox
                                                .getSelectedItem()
                        )
                );
    }

    @Test
    void todayButtonUsesCurrentDate()
            throws Exception {

        final TrackingController controller =
                mock(TrackingController.class);

        view.setTrackingController(
                controller
        );

        when(loggedInState.getUsername())
                .thenReturn("ethan");

        final LocalDate today =
                LocalDate.now();

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Today"
                ).doClick()
        );

        verify(controller)
                .execute(
                        eq("ethan"),
                        eq(today),
                        any(TrackingPeriod.class)
                );
    }

    @Test
    void backButtonNavigatesHome()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> findButton(
                        view,
                        "Back to Home"
                ).doClick()
        );

        verify(viewManagerModel)
                .setState(
                        HomeView.VIEW_NAME
                );

        verify(viewManagerModel)
                .firePropertyChanged();
    }

    @Test
    void unrelatedPropertyChangeIsIgnored()
            throws Exception {

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                trackingViewModel,
                                "other",
                                null,
                                trackingState
                        )
                )
        );

        assertFalse(
                containsLabelText(
                        view,
                        "Tracking summary loaded."
                )
        );
    }

    @Test
    void propertyChangeDisplaysTrackingError()
            throws Exception {

        when(trackingState.getErrorMessage())
                .thenReturn(
                        "Unable to load tracking data."
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                trackingViewModel,
                                "state",
                                null,
                                trackingState
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Unable to load tracking data."
                )
        );
    }

    @Test
    void successfulStateUpdatesSummaryLabels()
            throws Exception {

        final LocalDate start =
                LocalDate.of(
                        2026,
                        8,
                        4
                );

        final LocalDate end =
                LocalDate.of(
                        2026,
                        8,
                        10
                );

        when(trackingState.getErrorMessage())
                .thenReturn("");

        when(trackingState.getStartDate())
                .thenReturn(start);

        when(trackingState.getEndDate())
                .thenReturn(end);

        when(trackingState.getCaloriesConsumed())
                .thenReturn(2000.0);

        when(trackingState.getCaloriesBurned())
                .thenReturn(300.0);

        when(trackingState.getNetCalories())
                .thenReturn(1700.0);

        when(trackingState.getProteinInGrams())
                .thenReturn(100.0);

        when(trackingState.getCarbohydratesInGrams())
                .thenReturn(250.0);

        when(trackingState.getFatInGrams())
                .thenReturn(60.0);

        when(trackingState.getFiberInGrams())
                .thenReturn(30.0);

        when(trackingState.getSugarInGrams())
                .thenReturn(40.0);

        when(trackingState.getSodiumInMilligrams())
                .thenReturn(1500.0);

        when(trackingState.getAverageCaloriesConsumed())
                .thenReturn(1900.0);

        when(trackingState.getAverageCaloriesBurned())
                .thenReturn(250.0);

        when(trackingState.getAverageNetCalories())
                .thenReturn(1650.0);

        when(trackingState.getDailySummaries())
                .thenReturn(List.of());

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                trackingViewModel,
                                "state",
                                null,
                                trackingState
                        )
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        start + " to " + end
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "2000.0 kcal"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "300.0 kcal"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "1700.0 kcal"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "100.0 g"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "1500.0 mg"
                )
        );

        assertTrue(
                containsLabelText(
                        view,
                        "Tracking summary loaded."
                )
        );
    }

    @Test
    void successfulStatePopulatesDailyTable()
            throws Exception {

        final DailyTrackingSummary first =
                mock(DailyTrackingSummary.class);

        final DailyTrackingSummary second =
                mock(DailyTrackingSummary.class);

        when(first.getDate())
                .thenReturn(
                        LocalDate.of(
                                2026,
                                8,
                                9
                        )
                );

        when(first.getCaloriesConsumed())
                .thenReturn(2000.0);

        when(first.getCaloriesBurned())
                .thenReturn(200.0);

        when(first.getNetCalories())
                .thenReturn(1800.0);

        when(second.getDate())
                .thenReturn(
                        LocalDate.of(
                                2026,
                                8,
                                10
                        )
                );

        when(second.getCaloriesConsumed())
                .thenReturn(2100.0);

        when(second.getCaloriesBurned())
                .thenReturn(300.0);

        when(second.getNetCalories())
                .thenReturn(1800.0);

        prepareSuccessfulState();

        when(trackingState.getDailySummaries())
                .thenReturn(
                        List.of(
                                first,
                                second
                        )
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                trackingViewModel,
                                "state",
                                null,
                                trackingState
                        )
                )
        );

        final JTable table =
                findComponent(
                        view,
                        JTable.class
                );

        assertEquals(
                2,
                table.getRowCount()
        );

        assertEquals(
                LocalDate.of(
                        2026,
                        8,
                        9
                ),
                table.getValueAt(
                        0,
                        0
                )
        );

        assertEquals(
                "2000.0 kcal",
                table.getValueAt(
                        0,
                        1
                )
        );

        assertEquals(
                "200.0 kcal",
                table.getValueAt(
                        0,
                        2
                )
        );

        assertEquals(
                "1800.0 kcal",
                table.getValueAt(
                        0,
                        3
                )
        );
    }

    @Test
    void secondSuccessfulUpdateReplacesOldTableRows()
            throws Exception {

        final DailyTrackingSummary first =
                mock(DailyTrackingSummary.class);

        when(first.getDate())
                .thenReturn(
                        LocalDate.of(
                                2026,
                                8,
                                10
                        )
                );

        when(first.getCaloriesConsumed())
                .thenReturn(1000.0);

        when(first.getCaloriesBurned())
                .thenReturn(100.0);

        when(first.getNetCalories())
                .thenReturn(900.0);

        prepareSuccessfulState();

        when(trackingState.getDailySummaries())
                .thenReturn(
                        List.of(first)
                );

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                trackingViewModel,
                                "state",
                                null,
                                trackingState
                        )
                )
        );

        final JTable table =
                findComponent(
                        view,
                        JTable.class
                );

        assertEquals(
                1,
                table.getRowCount()
        );

        when(trackingState.getDailySummaries())
                .thenReturn(List.of());

        SwingUtilities.invokeAndWait(
                () -> view.propertyChange(
                        new PropertyChangeEvent(
                                trackingViewModel,
                                "state",
                                null,
                                trackingState
                        )
                )
        );

        assertEquals(
                0,
                table.getRowCount()
        );
    }

    private void prepareSuccessfulState() {
        when(trackingState.getErrorMessage())
                .thenReturn("");

        when(trackingState.getStartDate())
                .thenReturn(
                        LocalDate.of(
                                2026,
                                8,
                                10
                        )
                );

        when(trackingState.getEndDate())
                .thenReturn(
                        LocalDate.of(
                                2026,
                                8,
                                10
                        )
                );

        when(trackingState.getCaloriesConsumed())
                .thenReturn(0.0);

        when(trackingState.getCaloriesBurned())
                .thenReturn(0.0);

        when(trackingState.getNetCalories())
                .thenReturn(0.0);

        when(trackingState.getProteinInGrams())
                .thenReturn(0.0);

        when(trackingState.getCarbohydratesInGrams())
                .thenReturn(0.0);

        when(trackingState.getFatInGrams())
                .thenReturn(0.0);

        when(trackingState.getFiberInGrams())
                .thenReturn(0.0);

        when(trackingState.getSugarInGrams())
                .thenReturn(0.0);

        when(trackingState.getSodiumInMilligrams())
                .thenReturn(0.0);

        when(trackingState.getAverageCaloriesConsumed())
                .thenReturn(0.0);

        when(trackingState.getAverageCaloriesBurned())
                .thenReturn(0.0);

        when(trackingState.getAverageNetCalories())
                .thenReturn(0.0);
    }

    private static JButton findButton(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton button
                    && text.equals(
                    button.getText()
            )) {

                return button;
            }

            if (component instanceof Container child) {
                try {
                    return findButton(
                            child,
                            text
                    );
                }
                catch (AssertionError ignored) {
                    // Continue searching.
                }
            }
        }

        throw new AssertionError(
                "Button not found: " + text
        );
    }

    private static boolean containsLabelText(
            Container container,
            String text) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JLabel label
                    && text.equals(
                    label.getText()
            )) {

                return true;
            }

            if (component instanceof Container child
                    && containsLabelText(
                    child,
                    text
            )) {

                return true;
            }
        }

        return false;
    }

    private static <T extends Component>
    T findComponent(
            Container container,
            Class<T> type) {

        final List<T> components =
                findComponents(
                        container,
                        type
                );

        if (components.isEmpty()) {
            throw new AssertionError(
                    "Component not found: "
                            + type.getSimpleName()
            );
        }

        return components.get(0);
    }

    private static <T extends Component>
    List<T> findComponents(
            Container container,
            Class<T> type) {

        final List<T> result =
                new ArrayList<>();

        collectComponents(
                container,
                type,
                result
        );

        return result;
    }

    private static <T extends Component>
    void collectComponents(
            Container container,
            Class<T> type,
            List<T> result) {

        for (Component component :
                container.getComponents()) {

            if (type.isInstance(component)) {
                result.add(
                        type.cast(component)
                );
            }

            if (component instanceof Container child) {
                collectComponents(
                        child,
                        type,
                        result
                );
            }
        }
    }
}
