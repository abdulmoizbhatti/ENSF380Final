package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WeatherTimePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel timeLabel;
    private JLabel temperatureLabel;
    private Timer timer;

    public WeatherTimePanel(String cityCode) {
        setPreferredSize(new Dimension(200, 300));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridLayout(2, 1, 0, 0)); 

        timeLabel = new JLabel("Loading...", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeLabel.setOpaque(true);
        timeLabel.setBackground(new Color(173, 216, 230)); 
        timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(timeLabel);

        temperatureLabel = new JLabel("Loading...", SwingConstants.CENTER);
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        temperatureLabel.setOpaque(true);
        temperatureLabel.setBackground(new Color(255, 228, 196));
        temperatureLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(temperatureLabel);

        fetchWeatherData(cityCode);
        startClock();
    }

    private void fetchWeatherData(String cityCode) {
        SwingUtilities.invokeLater(() -> {
            try {
                Document doc = Jsoup.connect("http://wttr.in/" + cityCode + "?format=%C+%t").get();
                String weather = doc.text();

                Pattern pattern = Pattern.compile("^(.*)\\s(\\+?\\-?\\d+°C)$");
                Matcher matcher = pattern.matcher(weather);

                if (matcher.find()) {
                    String weatherCondition = matcher.group(1).trim();
                    String temperature = matcher.group(2).trim();
                    temperatureLabel.setText("Current: " + weatherCondition + " " + temperature);
                } else {
                    temperatureLabel.setText("Error parsing weather data");
                }
            } catch (IOException e) {
                temperatureLabel.setText("Error fetching data");
                e.printStackTrace();
            }
        });
    }

    private void startClock() {
        timer = new Timer(1000, e -> updateClock()); 
        timer.setInitialDelay(0);
        timer.start();
    }

    private void updateClock() {
        SwingUtilities.invokeLater(() -> {
            try {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a"); 
                String formattedTime = now.format(formatter);
                timeLabel.setText(formattedTime);
            } catch (Exception e) {
                timeLabel.setText("Error fetching time");
                e.printStackTrace();
            }
        });
    }
}