package enery.data_access.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import enery.entity.user.CommonUserFactory;
import enery.entity.user.User;
import enery.entity.user.UserFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUserDataAccessObjectTest {

    @TempDir
    Path tempDirectory;

    private Path csvPath;
    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        csvPath =
                tempDirectory.resolve(
                        "users.csv"
                );

        userFactory =
                new CommonUserFactory();
    }

    @Test
    void newEmptyFileCreatesHeaderTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        final List<String> lines =
                Files.readAllLines(
                        csvPath
                );

        assertEquals(
                1,
                lines.size()
        );

        assertEquals(
                "username,password",
                lines.get(0)
        );

        assertNull(
                dao.get("Ethan")
        );
    }

    @Test
    void saveStoresUserInMemoryTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        final User user =
                userFactory.create(
                        "Ethan",
                        "password123"
                );

        dao.save(user);

        assertTrue(
                dao.existsByName(
                        "Ethan"
                )
        );

        assertEquals(
                "Ethan",
                dao.get("Ethan")
                        .getName()
        );

        assertEquals(
                "password123",
                dao.get("Ethan")
                        .getPassword()
        );
    }

    @Test
    void saveWritesUserToCsvTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        final User user =
                userFactory.create(
                        "Ethan",
                        "abc123"
                );

        dao.save(user);

        final List<String> lines =
                Files.readAllLines(
                        csvPath
                );

        assertEquals(
                2,
                lines.size()
        );

        assertEquals(
                "username,password",
                lines.get(0)
        );

        assertEquals(
                "Ethan,abc123",
                lines.get(1)
        );
    }

    @Test
    void constructorLoadsExistingUsersTest()
            throws IOException {

        Files.writeString(
                csvPath,
                "username,password\n"
                        + "Ethan,password1\n"
                        + "Alice,password2\n"
        );

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        assertTrue(
                dao.existsByName(
                        "Ethan"
                )
        );

        assertTrue(
                dao.existsByName(
                        "Alice"
                )
        );

        assertEquals(
                "password1",
                dao.get("Ethan")
                        .getPassword()
        );

        assertEquals(
                "password2",
                dao.get("Alice")
                        .getPassword()
        );
    }

    @Test
    void getUnknownUserReturnsNullTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        assertNull(
                dao.get(
                        "Unknown"
                )
        );
    }

    @Test
    void existsByNameReturnsFalseForUnknownUserTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        assertFalse(
                dao.existsByName(
                        "Unknown"
                )
        );
    }

    @Test
    void multipleUsersAreSavedTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        dao.save(
                userFactory.create(
                        "Ethan",
                        "password1"
                )
        );

        dao.save(
                userFactory.create(
                        "Alice",
                        "password2"
                )
        );

        assertTrue(
                dao.existsByName(
                        "Ethan"
                )
        );

        assertTrue(
                dao.existsByName(
                        "Alice"
                )
        );

        final List<String> lines =
                Files.readAllLines(
                        csvPath
                );

        assertEquals(
                3,
                lines.size()
        );
    }

    @Test
    void savedUsersRemainAfterReloadTest()
            throws IOException {

        FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        dao.save(
                userFactory.create(
                        "Ethan",
                        "persistentPassword"
                )
        );

        dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        assertTrue(
                dao.existsByName(
                        "Ethan"
                )
        );

        assertEquals(
                "persistentPassword",
                dao.get("Ethan")
                        .getPassword()
        );
    }

    @Test
    void changePasswordReplacesStoredUserTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        dao.save(
                userFactory.create(
                        "Ethan",
                        "oldPassword"
                )
        );

        final User changedUser =
                userFactory.create(
                        "Ethan",
                        "newPassword"
                );

        dao.changePassword(
                changedUser
        );

        assertEquals(
                "newPassword",
                dao.get("Ethan")
                        .getPassword()
        );
    }

    @Test
    void changePasswordPersistsToFileTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        dao.save(
                userFactory.create(
                        "Ethan",
                        "oldPassword"
                )
        );

        dao.changePassword(
                userFactory.create(
                        "Ethan",
                        "newPassword"
                )
        );

        final FileUserDataAccessObject reloadedDao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        assertEquals(
                "newPassword",
                reloadedDao.get("Ethan")
                        .getPassword()
        );
    }

    @Test
    void invalidHeaderThrowsRuntimeExceptionTest()
            throws IOException {

        Files.writeString(
                csvPath,
                "name,secret\n"
                        + "Ethan,password\n"
        );

        assertThrows(
                RuntimeException.class,
                () -> new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                )
        );
    }

    @Test
    void setCurrentUsernameCurrentlyDoesNothingTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        dao.setCurrentUsername(
                "Ethan"
        );

        assertNull(
                dao.getCurrentUsername()
        );
    }

    @Test
    void currentUsernameInitiallyNullTest()
            throws IOException {

        final FileUserDataAccessObject dao =
                new FileUserDataAccessObject(
                        csvPath.toString(),
                        userFactory
                );

        assertNull(
                dao.getCurrentUsername()
        );
    }
}
