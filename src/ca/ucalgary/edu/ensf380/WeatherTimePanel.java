package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WeatherTimePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel timeLabel;
    private JLabel temperatureLabel;
    private Timer timer;
    private ZoneId zoneId;

    public WeatherTimePanel(String city) {
        setPreferredSize(new Dimension(200, 300));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;

        timeLabel = new JLabel("Loading...", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeLabel.setOpaque(true);
        timeLabel.setBackground(new Color(173, 216, 230));
        timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(timeLabel, gbc);

        temperatureLabel = new JLabel("<html><div style='text-align: center;'>Loading...</div></html>", SwingConstants.CENTER);
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        temperatureLabel.setOpaque(true);
        temperatureLabel.setBackground(new Color(255, 228, 196));
        temperatureLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(temperatureLabel, gbc);

        this.zoneId = ZoneId.of("America/Edmonton");

        fetchWeatherData(city);
        startClock();
    }

    private void fetchWeatherData(String city) {
        SwingUtilities.invokeLater(() -> {
            try {
                Document doc = Jsoup.connect("http://wttr.in/" + city + "?format=%C+%t").get();
                String weather = doc.text();

                Pattern pattern = Pattern.compile("^(.*)\\s(\\+?\\-?\\d+°C)$");
                Matcher matcher = pattern.matcher(weather);

                if (matcher.find()) {
                    String weatherCondition = matcher.group(1).trim();
                    String temperature = matcher.group(2).trim();
                    temperatureLabel.setText("<html><div style='text-align: center;'>Current: " + weatherCondition + "<br>" + temperature + "</div></html>");
                } else {
                    temperatureLabel.setText("<html><div style='text-align: center;'>Error parsing weather data</div></html>");
                }
            } catch (IOException e) {
                temperatureLabel.setText("<html><div style='text-align: center;'>Error fetching data</div></html>");
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
                ZonedDateTime now = ZonedDateTime.now(zoneId);
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