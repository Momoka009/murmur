package logic;

import dao.PostDAO;
import model.PostBean;

public class EditDiaryLogic {

    public PostBean getPostById(int postId) {
        PostDAO dao = new PostDAO();
        return dao.findPostById(postId);
    }

    public boolean updatePost(int postId, String title, String text, boolean isPublic) {
        PostDAO dao = new PostDAO();
        return dao.updatePost(postId, title, text, isPublic);
    }
}
