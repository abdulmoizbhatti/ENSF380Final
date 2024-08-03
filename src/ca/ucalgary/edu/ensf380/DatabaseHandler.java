package ca.ucalgary.edu.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ads";
    private static final String USER = "root"; 
    private static final String PASSWORD = "MoizBhatti.123"; 

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<String> getAdvertisementPaths() {
        List<String> paths = new ArrayList<>();
        String query = "SELECT file_path FROM MediaFiles";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                paths.add(rs.getString("file_path"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paths;
    }
}