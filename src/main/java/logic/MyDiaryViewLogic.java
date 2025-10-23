package logic;

import java.util.List;

import dao.PostDAO;
import model.PostBean;

public class MyDiaryViewLogic {

	public List<PostBean> getUserPosts(String userId) {
	    PostDAO dao = new PostDAO();
	    List<PostBean> posts = dao.findPostsByUserId(userId);

	    System.out.println("DAO returned posts size: " + (posts == null ? "null" : posts.size()));  // ここも確認

	    return posts;
	}
}