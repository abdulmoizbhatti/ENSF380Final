package ca.ucalgary.edu.ensf380.TestingFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ucalgary.edu.ensf380.WeatherTimePanel;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherTimePanelTest {
    private WeatherTimePanel weatherTimePanel;

    @BeforeEach
    void setUp() {
        weatherTimePanel = new WeatherTimePanel("Greece");
    }

    @Test
    void testFetchWeatherData() {
        assertNotNull(weatherTimePanel);
    }
}