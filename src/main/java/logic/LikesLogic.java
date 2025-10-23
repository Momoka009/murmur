package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.LikesDAO;
import model.PostBean;

public class LikesLogic {

    private LikesDAO likesDAO = new LikesDAO();

    public boolean likePost(String userId, int postId) {
        if (likesDAO.exists(userId, postId)) {
            return false; // すでにいいね済み
        }
        return likesDAO.insertLike(userId, postId);
    }

    public boolean unlikePost(String userId, int postId) {
        if (!likesDAO.exists(userId, postId)) {
            return false; // いいねしていない
        }
        return likesDAO.deleteLike(userId, postId);
    }
    
    public Map<Integer, Boolean> findLikedMap(String userId, List<PostBean> posts) {
        Map<Integer, Boolean> likedMap = new HashMap<>();
        for (PostBean post : posts) {
            boolean liked = likesDAO.exists(userId, post.getPost_id());
            likedMap.put(post.getPost_id(), liked);
        }
        return likedMap;
    }


}
