package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ThemesDAO;
import dao.UsersDAO;
import logic.PostLogic;
import logic.StreakLogic;
import model.PostBean;
import model.UsersBean;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // ここにランダムメッセージの配列を用意（DBなし）
    private static final String[] MESSAGES = {
        "The secret of getting ahead is getting started.（成功の秘訣は、始めることだ。）",
        "Well done is better than well said.（よくやることは、よく言うことよりも価値がある。）",
        "To live without Hope is to Cease to live.(希望を持たずに生きることは、死ぬことに等しい。)"
    };
    
    private String getRandomMessage() {
        Random rand = new Random();
        int index = rand.nextInt(MESSAGES.length);
        return MESSAGES[index];
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ThemesDAO themeDAO = new ThemesDAO();
        String randomTheme = themeDAO.findRandomTheme();
        request.setAttribute("randomTheme", randomTheme);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryPost.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        UsersBean loginUser = (UsersBean) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String title = request.getParameter("title");
        String text = request.getParameter("text");
        String isPublicStr = request.getParameter("isPublic");
        boolean isPublic = "true".equals(isPublicStr);

        if (title == null || title.isEmpty() || text == null || text.isEmpty()) {
            request.setAttribute("errorMessage", "タイトルと本文は必須です。");
            request.setAttribute("savedTitle", title);
            request.setAttribute("savedText", text);
            request.setAttribute("savedIsPublic", isPublic);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryPost.jsp");
            dispatcher.forward(request, response);
            return;
        }

        PostBean post = new PostBean();
        post.setUser(loginUser);
        post.setTitle(title);
        post.setText(text);
        post.setIs_public(isPublic);
        post.setCreated_at(LocalDateTime.now());

        PostLogic postLogic = new PostLogic();
        boolean result = postLogic.createPost(post);

        if (result) {

            // 連続投稿日数を計算
            int newStreak = StreakLogic.calculateStreak(loginUser.getCurrentStreak(), loginUser.getLast_post_date());
            
            System.out.println("旧連続日数: " + loginUser.getCurrentStreak());
			System.out.println("新連続日数: " + newStreak);
        	System.out.println("旧最終投稿日: " + loginUser.getLast_post_date());
        	System.out.println("更新日: " + LocalDate.now());
            // DBを更新
            UsersDAO usersDao = new UsersDAO();
            usersDao.updateStreakAndLastPostDate(loginUser.getUserId(), newStreak, LocalDate.now());

            // セッション内のユーザ情報も更新
            loginUser.setCurrentStreak(newStreak);
            loginUser.setLast_post_date(LocalDate.now());
            session.setAttribute("loginUser", loginUser);

            // ランダムメッセージ
            String randomMessage = getRandomMessage();
            request.setAttribute("message", randomMessage);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryPostComplete.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "投稿に失敗しました。");
            request.setAttribute("savedTitle", title);
            request.setAttribute("savedText", text);
            request.setAttribute("savedIsPublic", isPublic);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/diaryPost.jsp");
            dispatcher.forward(request, response);
        }
    }
}