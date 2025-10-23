package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemesDAO {

    public String findRandomTheme() {
        String sql = "SELECT theme FROM themes ORDER BY RAND() LIMIT 1";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getString("theme");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "日記";  // デフォルトテーマ
    }
}
