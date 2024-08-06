package ca.ucalgary.edu.ensf380.TestingFiles;

import org.junit.jupiter.api.Test;

import ca.ucalgary.edu.ensf380.DatabaseHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseHandlerTest {
    @Test
    void testGetConnection() {
        assertDoesNotThrow(() -> DatabaseHandler.getConnection());
    }

    @Test
    void testGetAdvertisementPaths() {
        List<String> paths = DatabaseHandler.getAdvertisementPaths();
        assertNotNull(paths);
        assertFalse(paths.isEmpty());
    }
}