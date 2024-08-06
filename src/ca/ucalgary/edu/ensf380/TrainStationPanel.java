/*
 * Author: Moiz Bhatti
 * Everything related to displaying the train station dynamic panel
 */

package ca.ucalgary.edu.ensf380;

// imports that were going to be used for audio file, see end of file for more info
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.LineEvent;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class TrainStationPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel directionLabel;
    private JLabel[] stationLabels;
    private Map<String, TrainDataUtil.Station> stations;
    private int trainNumber;
    private boolean stopUpdating = false;
    private boolean isFirstUpdate = true;

    public TrainStationPanel(Map<String, TrainDataUtil.Station> stations, int trainNumber) {
        this.stations = stations;
        this.trainNumber = trainNumber;
        setPreferredSize(new Dimension(800, 100));
        setLayout(new BorderLayout());

        directionLabel = new JLabel("Next: (Next Station)", SwingConstants.CENTER);
        directionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(directionLabel, BorderLayout.SOUTH);

        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(new GridLayout(1, 5));

        stationLabels = new JLabel[5];
        for (int i = 0; i < stationLabels.length; i++) {
            stationLabels[i] = new JLabel("", SwingConstants.CENTER);
            stationLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
            stationPanel.add(stationLabels[i]);
        }

        add(stationPanel, BorderLayout.CENTER);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!stopUpdating) {
                    List<TrainDataUtil.TrainPosition> positions = TrainDataUtil.loadLatestTrainPositions();
                    SwingUtilities.invokeLater(() -> updateTrainPositions(positions));
                }
            }
        }, 0, 15000);
    }

    public void updateTrainPositions(List<TrainDataUtil.TrainPosition> positions) {
        TrainDataUtil.TrainPosition trainPosition = positions.stream()
                .filter(tp -> tp.getTrainNumber() == trainNumber)
                .findFirst()
                .orElse(null);

        if (trainPosition != null) {
            System.out.println("Train " + trainNumber + " Current Position: " + trainPosition.getStationCode() + " Destination: " + trainPosition.getDestination());
            
            if (trainPosition.getStationCode().equals(trainPosition.getDestination())) {
                stopUpdating = true;
                directionLabel.setText("End of Line");
                System.out.println("Train " + trainNumber + " has reached its destination: " + trainPosition.getDestination());
                return;
            }

            String currentStationCode = trainPosition.getStationCode();
            String nextStationCode = getNextStationCode(trainPosition);
            directionLabel.setText("Next: " + getStationInfo(nextStationCode));

            String[] nextStations = getNextStations(trainPosition);
            for (int i = 0; i < stationLabels.length; i++) {
                stationLabels[i].setText(nextStations[i]);
            }
        }
    }

    private String[] getNextStations(TrainDataUtil.TrainPosition trainPosition) {
        String[] nextStations = new String[5];
        String currentStationCode = trainPosition.getStationCode();
        nextStations[0] = stations.get(currentStationCode).getStationName();

        List<TrainDataUtil.Station> stationList = stations.values().stream()
                .filter(station -> station.getLineName().equals(trainPosition.getLineName()))
                .sorted((s1, s2) -> {
                    int num1 = Integer.parseInt(s1.getStationCode().substring(1));
                    int num2 = Integer.parseInt(s2.getStationCode().substring(1));
                    return Integer.compare(num1, num2);
                })
                .collect(Collectors.toList());

        int currentIndex = stationList.indexOf(stations.get(currentStationCode));

        for (int i = 1; i < 5; i++) {
            int nextIndex = currentIndex + (trainPosition.getDirection().equals("forward") ? i : -i);
            if (nextIndex >= 0 && nextIndex < stationList.size()) {
                nextStations[i] = stationList.get(nextIndex).getStationName();
            } else {
                nextStations[i] = "End of Line";
            }
        }

        if (isFirstUpdate) {
            nextStations[0] = "<html>&#x2192; Train<br>" + nextStations[0] + "</html>";
            isFirstUpdate = false;
        } else if (nextStations.length > 1) {
            nextStations[1] = "<html>&#x2192; Train<br>" + nextStations[1] + "</html>";
        }

        return nextStations;
    }

    private String getNextStationCode(TrainDataUtil.TrainPosition trainPosition) {
        List<TrainDataUtil.Station> stationList = stations.values().stream()
                .filter(station -> station.getLineName().equals(trainPosition.getLineName()))
                .sorted((s1, s2) -> {
                    int num1 = Integer.parseInt(s1.getStationCode().substring(1));
                    int num2 = Integer.parseInt(s2.getStationCode().substring(1));
                    return Integer.compare(num1, num2);
                })
                .collect(Collectors.toList());

        int currentIndex = stationList.indexOf(stations.get(trainPosition.getStationCode()));
        int nextIndex = currentIndex + (trainPosition.getDirection().equals("forward") ? 1 : -1);

        if (nextIndex >= 0 && nextIndex < stationList.size()) {
            return stationList.get(nextIndex).getStationCode();
        } else {
            return trainPosition.getStationCode();
        }
    }

    private String getStationInfo(String stationCode) {
        TrainDataUtil.Station station = stations.get(stationCode);
        return stationCode + "(" + station.getStationName() + ")";
    }
}

    /* Code was going to be used to access mp3 files, but that file type was not allowed and wasn't able to convert them all to wav in time
     * 
    private void playStationAnnouncement(String stationName, String lineName) {
        try {
            String sanitizedStationName = stationName.replaceAll("\\s", "");
            File audioFile = new File("audio/" + sanitizedStationName + lineName + ".mp3");
            if (!audioFile.exists()) {
                audioFile = new File("audio/" + sanitizedStationName + ".mp3");
            }
            System.out.println("Looking for file: " + audioFile.getAbsolutePath());
            if (audioFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } else {
                System.err.println("Audio file not found for station: " + sanitizedStationName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
