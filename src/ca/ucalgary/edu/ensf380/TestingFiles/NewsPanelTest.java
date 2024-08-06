package ca.ucalgary.edu.ensf380.TestingFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ucalgary.edu.ensf380.NewsPanel;

import static org.junit.jupiter.api.Assertions.*;

public class NewsPanelTest {
    private NewsPanel newsPanel;

    @BeforeEach
    void setUp() {
        newsPanel = new NewsPanel("animal");
    }

    @Test
    void testFetchNews() {
        assertNotNull(newsPanel);
    }
}