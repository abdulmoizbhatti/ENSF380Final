/*
 * Author: Moiz Bhatti
 * Main method where everything converges to run the app
 */

package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class Main2 extends JFrame {
    private static final long serialVersionUID = 1L;
    private TrainStationPanel trainStationPanel;

    public Main2(String city, String keyword, int trainNumber) {
        setTitle("Subway Screen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        AdvertisementPanel advertisementPanel = new AdvertisementPanel();
        topPanel.add(advertisementPanel, BorderLayout.CENTER);

        WeatherTimePanel weatherTimePanel = new WeatherTimePanel(city);
        topPanel.add(weatherTimePanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        NewsPanel newsPanel = new NewsPanel(keyword);
        add(newsPanel, BorderLayout.CENTER);

        Map<String, TrainDataUtil.Station> stations = TrainDataUtil.loadStations();
        trainStationPanel = new TrainStationPanel(stations, trainNumber);
        add(trainStationPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Please provide the city, news keyword, and train number as command line arguments.");
            System.exit(1);
        }
        String city = args[0];
        String keyword = args[1];
        int trainNumber = Integer.parseInt(args[2]);
        SwingUtilities.invokeLater(() -> new Main2(city, keyword, trainNumber));
    }
}