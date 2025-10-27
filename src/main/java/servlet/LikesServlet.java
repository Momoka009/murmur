package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.LikesLogic;
import model.UsersBean;

@WebServlet("/LikesServlet")
public class LikesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UsersBean loginUser = (UsersBean) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        int postId = Integer.parseInt(request.getParameter("postId"));
        String action = request.getParameter("action");
        String userId = loginUser.getUserId();

        LikesLogic likesLogic = new LikesLogic();
        boolean success = false;

        if ("like".equals(action)) {
            success = likesLogic.likePost(userId, postId);
        } else if ("unlike".equals(action)) {
            success = likesLogic.unlikePost(userId, postId);
        }

        // 処理結果は必要に応じて扱う（今はリダイレクトだけ）
        response.sendRedirect("SomeonesDiaryViewServlet");
    }
}

