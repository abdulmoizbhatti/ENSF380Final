package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import java.awt.*;

public class NewsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextArea newsArea;

    public NewsPanel() {
        newsArea = new JTextArea("News");
        newsArea.setEditable(false);
        newsArea.setLineWrap(true);
        newsArea.setWrapStyleWord(true);
        newsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        newsArea.setBackground(new Color(238, 238, 238));
        JScrollPane newsScrollPane = new JScrollPane(newsArea);
        newsScrollPane.setPreferredSize(new Dimension(600, 50));
        setLayout(new BorderLayout());
        add(newsScrollPane, BorderLayout.CENTER);
    }

}