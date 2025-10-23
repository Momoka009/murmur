package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LikesBean implements Serializable {
    private int like_id;
    private String user_id;     
    private int post_id;
    private LocalDateTime datetime;
    
    public int getLike_id() {
        return like_id;
    }
    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public int getPost_id() {
        return post_id;
    }
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
