package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2 extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextArea outputArea;
    private JButton startButton;
    private JButton stopButton;
    private Process process;
    private ExecutorService executor;

    private AdvertisementPanel advertisementPanel;
    private WeatherTimePanel weatherTimePanel;
    private NewsPanel newsPanel;
    private TrainStationPanel trainStationPanel;

    public Main2() {
        setTitle("Subway Screen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stopProcess();
                dispose();
            }
        });

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        advertisementPanel = new AdvertisementPanel();
        topPanel.add(advertisementPanel, BorderLayout.CENTER);

        weatherTimePanel = new WeatherTimePanel();
        topPanel.add(weatherTimePanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        newsPanel = new NewsPanel();
        add(newsPanel, BorderLayout.CENTER);

        trainStationPanel = new TrainStationPanel();
        add(trainStationPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setPreferredSize(new Dimension(100, 38));
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
        stopButton.setPreferredSize(new Dimension(100, 38));
        buttonPanel.add(stopButton);

        add(buttonPanel, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startProcess();
        } else if (e.getSource() == stopButton) {
            stopProcess();
        }
    }

    private void startProcess() {
        if (process == null) {
            try {
                ProcessBuilder builder = new ProcessBuilder("java", "-jar", "./exe/SubwaySimulator.jar", "--in", "./data/subway.csv", "--out", "./out");
                builder.redirectErrorStream(true);
                process = builder.start();

                executor.execute(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            outputArea.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                executor.execute(() -> {
                    try {
                        process.waitFor();
                        process = null;
                        SwingUtilities.invokeLater(() -> {
                            stopButton.setEnabled(false);
                            startButton.setEnabled(true);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                stopButton.setEnabled(true);
                startButton.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopProcess() {
        if (process != null) {
            process.destroy();
            process = null;
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main2::new);
    }
}