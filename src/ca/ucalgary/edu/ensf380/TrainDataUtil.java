package ca.ucalgary.edu.ensf380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class TrainDataUtil {

    private static final String SUBWAY_CSV = "./data/subway.csv";
    private static final String OUT_DIR = "./out";

    public static Map<String, Station> loadStations() {
        Map<String, Station> stations = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SUBWAY_CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String lineName = parts[1];
                    String stationCode = parts[3];
                    String stationName = parts[4];
                    stations.put(stationCode, new Station(lineName, stationCode, stationName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    public static List<TrainPosition> loadLatestTrainPositions() {
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(OUT_DIR), "*.csv");
            Path latestFile = null;
            for (Path path : directoryStream) {
                if (latestFile == null || Files.getLastModifiedTime(path).compareTo(Files.getLastModifiedTime(latestFile)) > 0) {
                    latestFile = path;
                }
            }
            if (latestFile != null) {
                return loadTrainPositions(latestFile.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<TrainPosition> loadTrainPositions(String filePath) {
        List<TrainPosition> positions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String lineName = parts[0];
                    int trainNumber = Integer.parseInt(parts[1]);
                    String stationCode = parts[2];
                    String direction = parts[3];
                    String destination = parts[4];
                    positions.add(new TrainPosition(lineName, trainNumber, stationCode, direction, destination));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return positions;
    }

    public static class Station {
        String lineName;
        String stationCode;
        String stationName;

        public Station(String lineName, String stationCode, String stationName) {
            this.lineName = lineName;
            this.stationCode = stationCode;
            this.stationName = stationName;
        }

        public String getLineName() {
            return lineName;
        }

        public String getStationCode() {
            return stationCode;
        }

        public String getStationName() {
            return stationName;
        }
    }

    public static class TrainPosition {
        String lineName;
        int trainNumber;
        String stationCode;
        String direction;
        String destination;

        public TrainPosition(String lineName, int trainNumber, String stationCode, String direction, String destination) {
            this.lineName = lineName;
            this.trainNumber = trainNumber;
            this.stationCode = stationCode;
            this.direction = direction;
            this.destination = destination;
        }

        public String getLineName() {
            return lineName;
        }

        public int getTrainNumber() {
            return trainNumber;
        }

        public String getStationCode() {
            return stationCode;
        }

        public String getDirection() {
            return direction;
        }

        public String getDestination() {
            return destination;
        }
    }
}