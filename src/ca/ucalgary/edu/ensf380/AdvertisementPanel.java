package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;

public class AdvertisementPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public AdvertisementPanel() {
        setBackground(Color.BLUE); 
        JLabel adLabel = new JLabel("Advertisement");
        adLabel.setForeground(Color.WHITE);
        adLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(adLabel);
    }
}