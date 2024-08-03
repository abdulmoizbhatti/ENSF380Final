package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;

public class WeatherTimePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public WeatherTimePanel() {
        setPreferredSize(new Dimension(200, 300));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridLayout(2, 1));

        JLabel timeLabel = new JLabel("8:16 pm", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        add(timeLabel);

        JLabel temperatureLabel = new JLabel("Current: 0°C", SwingConstants.CENTER);
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(temperatureLabel);
    }

}