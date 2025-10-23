package logic;

import dao.UsersDAO;
import model.UsersBean;

public class LoginLogic {

    public boolean execute(String userId, String password) {
        UsersDAO dao = new UsersDAO();
        UsersBean user = dao.findByUserId(userId);

        if (user != null && user.getPassword().equals(password)) {
            return true;  // 認証成功
        }
        return false; // 認証失敗
    }

    // 必要なら、認証成功時にユーザ情報を返す別メソッドを作るのもあり
    public UsersBean getUser(String userId) {
        UsersDAO dao = new UsersDAO();
        return dao.findByUserId(userId);
    }
}
