package enery.data_access.database;

import enery.entity.user.CommonUserFactory;
import enery.entity.user.UserFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class DBUserDataAccessObjectTest {

    private DBUserDataAccessObject dataAccessObject;

    @BeforeEach
    void setUp() {
        final UserFactory userFactory =
                new CommonUserFactory();

        dataAccessObject =
                new DBUserDataAccessObject(
                        userFactory
                );
    }

    @Test
    void currentUsernameInitiallyNullTest() {
        assertNull(
                dataAccessObject.getCurrentUsername()
        );
    }

    @Test
    void setCurrentUsernameCurrentlyDoesNothingTest() {
        dataAccessObject.setCurrentUsername(
                "Ethan"
        );

        assertNull(
                dataAccessObject.getCurrentUsername()
        );
    }
}
