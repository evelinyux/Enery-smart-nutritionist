package enery.use_case.logout;

import enery.data_access.InMemoryUserDataAccessObject;
import enery.entity.user.CommonUserFactory;
import enery.entity.user.User;
import enery.entity.user.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    @Test
    void successTest() {
        LogoutInputData inputData = new LogoutInputData("Paul");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);
        userRepository.setCurrentUsername("Paul");
        assertEquals("Paul", userRepository.getCurrentUsername());

        // This creates a successPresenter that tests whether the test case is as we expect.
        final boolean[] successViewCalled = {false};
        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                successViewCalled[0] = true;
                // check that the output data contains the username of who logged out
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
        // check that the user was logged out
        assertTrue(successViewCalled[0]);
        assertNull(userRepository.getCurrentUsername());
    }

}
