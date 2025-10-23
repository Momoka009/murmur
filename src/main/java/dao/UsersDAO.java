package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import model.UsersBean;

public class UsersDAO {

    // ユーザーIDの重複チェック
    public boolean existsByUserId(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ユーザー登録
    public boolean insert(UsersBean user) {
        String sql = "INSERT INTO users (user_id, nickname, password) VALUES (?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getNickname());
            ps.setString(3, user.getPassword());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ユーザー取得
    public UsersBean findByUserId(String userId) {
        UsersBean user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new UsersBean();
                    user.setUserId(rs.getString("user_id"));
                    user.setNickname(rs.getString("nickname"));
                    user.setPassword(rs.getString("password"));
                    user.setCurrentStreak(rs.getInt("current_streak"));
                    Date lastPostDate = rs.getDate("last_post_date");
                    if (lastPostDate != null) {
                        user.setLast_post_date(lastPostDate.toLocalDate());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // 連続投稿日数と最終投稿日更新
    public boolean updateStreakAndLastPostDate(String userId, int newStreak, LocalDate lastPostDate) {
        String sql = "UPDATE users SET current_streak = ?, last_post_date = ? WHERE user_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStreak);
            ps.setDate(2, Date.valueOf(lastPostDate));
            ps.setString(3, userId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // UsersBean丸ごと更新 (今後拡張用)
    public boolean updateUser(UsersBean user) {
        String sql = "UPDATE users SET nickname = ?, password = ?, current_streak = ?, last_post_date = ? WHERE user_id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getNickname());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getCurrentStreak());
            if (user.getLast_post_date() != null) {
                ps.setDate(4, Date.valueOf(user.getLast_post_date()));
            } else {
                ps.setDate(4, null);
            }
            ps.setString(5, user.getUserId());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
