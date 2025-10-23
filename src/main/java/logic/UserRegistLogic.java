package logic;

import dao.UsersDAO;
import model.UsersBean;

public class UserRegistLogic {


    // バリデーション用メソッド
    public String validate(UsersBean user, String passwordCheck) {
        if (user.getNickname() == null || user.getNickname().isEmpty()) {
            return "ニックネームを入力してください。";
        }
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            return "ユーザーIDを入力してください。";
        }
        if (user.getPassword() == null || user.getPassword().length() < 4) {
            return "パスワードは4文字以上で入力してください。";
        }
        if (!user.getPassword().equals(passwordCheck)) {
            return "パスワード（確認用）が一致しません。";
        }
        if (userExists(user.getUserId())) {
            return "そのユーザーIDは既に使われています。";
        }
        return null; // エラーなし
    }

    private boolean userExists(String userId) {
        UsersDAO dao = new UsersDAO();
        return dao.existsByUserId(userId);
    }

    // 登録処理
    public boolean regist(UsersBean user) {
        UsersDAO dao = new UsersDAO();
        return dao.insert(user);
    }
}
