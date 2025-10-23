package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostBean implements Serializable {
    private int post_id;
    private UsersBean user;      
    private String title;
    private String text;
    private boolean is_public;
    private LocalDateTime created_at;
    private int likeCount;  // いいね数を保持するフィールド

    // post_id
    public int getPost_id() {
        return post_id;
    }
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    // user
    public UsersBean getUser() {
        return user;
    }
    public void setUser(UsersBean user) {
        this.user = user;
    }

    // title
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // text
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    // is_public
    public boolean Is_public() {
        return is_public;
    }
    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    // created_at
    public LocalDateTime getCreated_at() {
        return created_at;
    }
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    // likeCount
    public int getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

}
