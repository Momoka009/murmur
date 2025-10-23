package logic;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import dao.PostDAO;
import dao.UsersDAO;
import model.PostBean;
import model.UsersBean;

public class PostLogic {
    
    public boolean createPost(PostBean post) {
        PostDAO postDao = new PostDAO();
        UsersDAO userDao = new UsersDAO();

        // 投稿保存
        boolean saved = postDao.insertPost(post);
        if (!saved) {
            return false;
        }

        // 投稿者情報を取得
        UsersBean user = post.getUser();

        // 現在の日付
        LocalDate today = LocalDate.now();

        // ユーザの最終投稿日
        LocalDate lastPostDate = user.getLast_post_date();

        int newStreak = 1; // デフォルト連続日数

        if (lastPostDate != null) {
            long diffDays = ChronoUnit.DAYS.between(lastPostDate, today);
            if (diffDays == 1) {
                // 前日投稿していたら連続日数+1
                newStreak = user.getCurrentStreak() + 1;
            } else if (diffDays > 1) {
                // 間が空いていたら1にリセット
                newStreak = 1;
            } else if (diffDays == 0) {
                // 同日投稿は連続日数変えない（2回目以降の投稿）
                newStreak = user.getCurrentStreak();
            }
        }

        user.setCurrentStreak(newStreak);
        user.setLast_post_date(today);

        // ユーザ情報更新
        userDao.updateUser(user);

        return true;
    }

    /**
     * 指定ユーザー以外の公開投稿一覧を取得する
     * @param excludeUserId ログインユーザーのID（除外するユーザー）
     * @return 公開投稿のリスト
     */
    public List<PostBean> getPublicPostsExcludeUser(String loginUserId) {
        PostDAO dao = new PostDAO();
        return dao.findPublicPostsExcludeUser(loginUserId);
    }

}
