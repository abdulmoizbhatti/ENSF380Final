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

    public AdvertisementPanel() {
        setBackground(Color.BLUE);
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

    private void showNextAd() {
        if (adPaths.isEmpty()) return;

        if (showMap) {
            adLabel.setText("Map Display");
            adLabel.setIcon(null);
        } else {
            String adPath = adPaths.get(currentAdIndex);
            ImageIcon adIcon = new ImageIcon(adPath);
            Image img = adIcon.getImage();
            Image resizedImg = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            adIcon = new ImageIcon(resizedImg);
            adLabel.setIcon(adIcon);
            adLabel.setText("");
            currentAdIndex = (currentAdIndex + 1) % adPaths.size();
        }
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