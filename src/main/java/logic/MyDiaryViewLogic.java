package logic;

import java.util.List;

import dao.PostDAO;
import model.PostBean;

public class MyDiaryViewLogic {

	public List<PostBean> getUserPosts(String userId) {
	    PostDAO dao = new PostDAO();
	    List<PostBean> posts = dao.findPostsByUserId(userId);

	    return posts;
	}
}