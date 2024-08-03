package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;

public class TrainStationPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public TrainStationPanel() {
        setPreferredSize(new Dimension(800, 100));
        setLayout(new BorderLayout());

        JLabel directionLabel = new JLabel("Next: Banff Trail", SwingConstants.CENTER);
        directionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(directionLabel, BorderLayout.SOUTH);

        JPanel stationPanel = new JPanel();
        stationPanel.setLayout(new GridLayout(1, 5));

        String[] stations = {"Lions Park", "Banff Trail", "University", "Brentwood", "Dalhousie"};
        for (String station : stations) {
            JLabel stationLabel = new JLabel(station, SwingConstants.CENTER);
            stationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            stationPanel.add(stationLabel);
        }

        add(stationPanel, BorderLayout.CENTER);
    }

}