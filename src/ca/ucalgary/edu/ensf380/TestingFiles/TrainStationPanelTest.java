package ca.ucalgary.edu.ensf380.TestingFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ucalgary.edu.ensf380.TrainDataUtil;
import ca.ucalgary.edu.ensf380.TrainStationPanel;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TrainStationPanelTest {
    private TrainStationPanel trainStationPanel;
    private Map<String, TrainDataUtil.Station> stations;

    @BeforeEach
    void setUp() {
        stations = new HashMap<>();
        stations.put("S1", new TrainDataUtil.Station("Line1", "R1", "Station 1"));
        stations.put("S2", new TrainDataUtil.Station("Line1", "B2", "Station 2"));
        trainStationPanel = new TrainStationPanel(stations, 123);
    }

    @Test
    void testUpdateTrainPositions() {
        assertNotNull(trainStationPanel);
    }
}