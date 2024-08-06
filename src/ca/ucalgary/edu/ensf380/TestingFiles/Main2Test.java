package ca.ucalgary.edu.ensf380.TestingFiles;

import org.junit.jupiter.api.Test;
import ca.ucalgary.edu.ensf380.Main2;

import static org.junit.jupiter.api.Assertions.*;

public class Main2Test {
    @Test
    void testMainMethod() {
        String[] args = {"New York", "technology", "123"};
        assertDoesNotThrow(() -> Main2.main(args));
    }
}