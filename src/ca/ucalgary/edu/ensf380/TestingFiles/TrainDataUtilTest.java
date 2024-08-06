package ca.ucalgary.edu.ensf380.TestingFiles;

import org.junit.jupiter.api.Test;

import ca.ucalgary.edu.ensf380.TrainDataUtil;

import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainDataUtilTest {
    @Test
    void testLoadStations() {
        Map<String, TrainDataUtil.Station> stations = TrainDataUtil.loadStations();
        assertNotNull(stations);
        assertFalse(stations.isEmpty());
    }

    @Test
    void testLoadLatestTrainPositions() {
        List<TrainDataUtil.TrainPosition> positions = TrainDataUtil.loadLatestTrainPositions();
        assertNotNull(positions);
        assertFalse(positions.isEmpty());
    }
}