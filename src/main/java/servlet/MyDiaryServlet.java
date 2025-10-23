package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.MyDiaryViewLogic;
import model.PostBean;
import model.UsersBean;

@WebServlet("/MyDiaryServlet")
public class MyDiaryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UsersBean user = (UsersBean) session.getAttribute("loginUser");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        
        String userId = user.getUserId();

        MyDiaryViewLogic logic = new MyDiaryViewLogic();
        List<PostBean> posts = logic.getUserPosts(userId);

        System.out.println("posts size: " + (posts == null ? "null" : posts.size()));  // ← ここで出力確認

        request.setAttribute("posts", posts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/myDiary.jsp");
        dispatcher.forward(request, response);
    }
}
