package ca.ucalgary.edu.ensf380.TestingFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ucalgary.edu.ensf380.AdvertisementPanel;
import ca.ucalgary.edu.ensf380.DatabaseHandler;

import javax.swing.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AdvertisementPanelTest {
    private AdvertisementPanel adPanel;

    @BeforeEach
    void setUp() {
        adPanel = new AdvertisementPanel();
    }

    @Test
    void testLoadAdvertisements() {
        assertNotNull(adPanel);
        assertEquals(Arrays.asList("path1", "path2"), DatabaseHandler.getAdvertisementPaths());
    }

    @Test
    void testShowNextAd() {
        adPanel.showNextAd();
        JLabel label = (JLabel) adPanel.getComponent(1);
        assertNotNull(label.getIcon());
    }
}