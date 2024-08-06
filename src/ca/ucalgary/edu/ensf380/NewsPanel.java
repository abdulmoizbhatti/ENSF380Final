/*
 * Author: Moiz Bhatti
 * Everything related to the news panel 
 */

package ca.ucalgary.edu.ensf380;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel newsLabel;
    private String apiKey = "4cfff678095a4fdebf75895d477cdb96"; 
    private String keyword;

    public NewsPanel(String keyword) {
        this.keyword = keyword;
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 2));

        newsLabel = new JLabel("Fetching news...");
        newsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        newsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        newsLabel.setBackground(Color.WHITE);
        newsLabel.setOpaque(true);

        JPanel containerPanel = new JPanel(null);
        containerPanel.setPreferredSize(new Dimension(600, 50));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(newsLabel);

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        fetchNews();
    }

    private void fetchNews() {
        new Thread(() -> {
            try {
                String apiUrl = "https://newsapi.org/v2/everything?q=" + keyword + "&apiKey=" + apiKey;
                URI uri = new URI(apiUrl);
                URL url = uri.toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    conn.disconnect();

                    JSONObject json = new JSONObject(content.toString());
                    JSONArray articles = json.getJSONArray("articles");
                    StringBuilder newsContent = new StringBuilder();
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject article = articles.getJSONObject(i);
                        newsContent.append(article.getString("title")).append(" | ");
                    }

                    SwingUtilities.invokeLater(() -> {
                        newsLabel.setText(newsContent.toString());
                        int newsWidth = newsLabel.getPreferredSize().width;
                        newsLabel.setBounds(getWidth(), 0, newsWidth, 50);
                        scrollNews();
                    });
                } else {
                    System.err.println("Failed to fetch news. HTTP Response Code: " + responseCode);
                    SwingUtilities.invokeLater(() -> {
                        newsLabel.setText("Failed to fetch news. HTTP Response Code: " + responseCode);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    newsLabel.setText("Failed to fetch news. Exception: " + e.getMessage());
                });
            }
        }).start();
    }

    private void scrollNews() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int x = newsLabel.getX();
            @Override
            public void run() {
                x--;
                newsLabel.setLocation(x, newsLabel.getY());
                if (x + newsLabel.getWidth() < 0) {
                    x = getWidth();
                }
            }
        }, 0, 25);
    }
}