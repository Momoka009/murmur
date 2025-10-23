package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.LikesLogic;
import logic.PostLogic;
import model.PostBean;
import model.UsersBean;

@WebServlet("/SomeonesDiaryViewServlet")
public class SomeonesDiaryViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UsersBean loginUser = (UsersBean) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        PostLogic postLogic = new PostLogic();
        LikesLogic likesLogic = new LikesLogic();

        List<PostBean> posts = postLogic.getPublicPostsExcludeUser(loginUser.getUserId());
        Map<Integer, Boolean> likedMap = likesLogic.findLikedMap(loginUser.getUserId(), posts);

        request.setAttribute("posts", posts);
        request.setAttribute("likedMap", likedMap);
        session.setAttribute("loginUserId", loginUser.getUserId()); // JSP用にIDをセット

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/someonesDiary.jsp");
        dispatcher.forward(request, response);
    }
}
