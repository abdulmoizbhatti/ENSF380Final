/*
 * Author: Moiz Bhatti
 * Code that retrieves information from database, and train map, and has everything related to the advertisement panel section
 */

package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdvertisementPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel adLabel;
    private List<String> adPaths;
    private int currentAdIndex = 0;
    private Timer timer;
    private boolean showMap = false;
    private static final int AD_DURATION = 10000; 
    private static final int MAP_DURATION = 5000;
    private static final String MAP_IMAGE_PATH = "map/Trains.png";

    public AdvertisementPanel() {
        setBackground(Color.BLACK); 
        setLayout(new BorderLayout());
        adLabel = new JLabel("", JLabel.CENTER);
        adLabel.setVerticalAlignment(JLabel.CENTER);
        add(adLabel, BorderLayout.CENTER);

        loadAdvertisements();

        timer = new Timer();
        scheduleNextTask(0);
    }

    private void loadAdvertisements() {
        adPaths = DatabaseHandler.getAdvertisementPaths();
    }

    public void showNextAd() {
        if (adPaths.isEmpty()) return;

        ImageIcon icon;
        if (showMap) {
            icon = new ImageIcon(MAP_IMAGE_PATH);
        } else {
            String adPath = adPaths.get(currentAdIndex);
            icon = new ImageIcon(adPath);
            currentAdIndex = (currentAdIndex + 1) % adPaths.size();
        }

        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(adLabel.getWidth(), adLabel.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);
        adLabel.setIcon(icon);
        adLabel.setText("");

        showMap = !showMap;
    }

    private void scheduleNextTask(int delay) {
        timer.schedule(new AdTask(), delay);
    }

    private class AdTask extends TimerTask {
        @Override
        public void run() {
            SwingUtilities.invokeLater(() -> {
                showNextAd();
                int delay = showMap ? AD_DURATION : MAP_DURATION; 
                scheduleNextTask(delay);
            });
        }
    }
}