package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.PostBean;
import model.UsersBean;

public class PostDAO {

    public List<PostBean> findPostsByUserId(String postId) {
        List<PostBean> postList = new ArrayList<>();

        String sql = "SELECT p.post_id, p.title, p.text, p.is_public, p.created_at, "
                   + "u.user_id, u.nickname, "
                   + "(SELECT COUNT(*) FROM likes l WHERE l.post_id = p.post_id) AS likeCount "
//                   likesテーブルから、その投稿に押された「いいね数」を数えて、likeCountとして取得
                   + "FROM posts p "
                   + "JOIN users u ON p.user_id = u.user_id " //投稿（posts）とユーザー（users）を結合
                   + "WHERE p.user_id = ? "
                   + "ORDER BY p.created_at DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, postId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PostBean post = new PostBean();

                    post.setPost_id(rs.getInt("post_id"));
                    post.setTitle(rs.getString("title"));
                    post.setText(rs.getString("text"));
                    post.setIs_public(rs.getBoolean("is_public"));
                    post.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());

                    UsersBean user = new UsersBean();
                    user.setUserId(rs.getString("user_id"));
                    user.setNickname(rs.getString("nickname"));
                    post.setUser(user);

                    post.setLikeCount(rs.getInt("likeCount"));

                    postList.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postList;
    }
    
    public boolean insertPost(PostBean post) {
        String sql = "INSERT INTO posts (user_id, title, text, is_public, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            UsersBean user = post.getUser();

            ps.setString(1, user.getUserId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getText());
            ps.setBoolean(4, post.Is_public());
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(post.getCreated_at()));

            int result = ps.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public PostBean findPostById(int postId) {
        String sql = "SELECT p.post_id, p.title, p.text, p.is_public, p.created_at, " +
                     "u.user_id, u.nickname " +
                     "FROM posts p JOIN users u ON p.user_id = u.user_id WHERE p.post_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PostBean post = new PostBean();
                    post.setPost_id(rs.getInt("post_id"));
                    post.setTitle(rs.getString("title"));
                    post.setText(rs.getString("text"));
                    post.setIs_public(rs.getBoolean("is_public"));
                    post.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());

                    UsersBean user = new UsersBean();
                    user.setUserId(rs.getString("user_id"));
                    user.setNickname(rs.getString("nickname"));
                    post.setUser(user);

                    return post;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePost(int postId, String title, String text, boolean isPublic) {
        String sql = "UPDATE posts SET title = ?, text = ?, is_public = ? WHERE post_id = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, text);
            ps.setBoolean(3, isPublic);
            ps.setInt(4, postId);

            int result = ps.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PostBean> findPublicPostsExcludeUser(String excludeUserId) {
        List<PostBean> list = new ArrayList<>();
        String sql = "SELECT p.post_id, p.user_id, u.nickname, p.title, p.text, p.is_public, p.created_at, "
                   + "(SELECT COUNT(*) FROM likes l WHERE l.post_id = p.post_id) AS likeCount "
                   + "FROM posts p "
                   + "JOIN users u ON p.user_id = u.user_id "
                   + "WHERE p.is_public = true AND p.user_id <> ? "
                   + "ORDER BY p.created_at DESC";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, excludeUserId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PostBean post = new PostBean();
                    post.setPost_id(rs.getInt("post_id"));
                    UsersBean user = new UsersBean();
                    user.setUserId(rs.getString("user_id"));
                    user.setNickname(rs.getString("nickname"));
                    post.setUser(user);
                    post.setTitle(rs.getString("title"));
                    post.setText(rs.getString("text"));
                    post.setIs_public(rs.getBoolean("is_public"));
                    post.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                    post.setLikeCount(rs.getInt("likeCount"));

                    list.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
