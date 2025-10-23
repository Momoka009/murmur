package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.EditDiaryLogic;
import model.PostBean;

@WebServlet("/EditDiaryServlet")
public class EditDiaryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postIdStr = request.getParameter("postId");

        if (postIdStr == null) {
            response.sendRedirect("MyDiaryServlet");
            return;
        }

        int postId;
        try {
            postId = Integer.parseInt(postIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("MyDiaryServlet");
            return;
        }

        EditDiaryLogic logic = new EditDiaryLogic();
        PostBean post = logic.getPostById(postId);

        if (post == null) {
            response.sendRedirect("MyDiaryServlet");
            return;
        }

        request.setAttribute("post", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editDiary.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int postId = Integer.parseInt(request.getParameter("postId"));
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        boolean isPublic = request.getParameter("isPublic") != null;

        EditDiaryLogic logic = new EditDiaryLogic();
        boolean updated = logic.updatePost(postId, title, text, isPublic);

        if (updated) {
            // 編集成功 → 完了画面にフォワード
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editDiaryComplete.jsp");
            dispatcher.forward(request, response);
        } else {
            // 失敗 → 編集画面に戻す（エラーメッセージ付き）
            PostBean post = logic.getPostById(postId);
            request.setAttribute("post", post);
            request.setAttribute("errorMessage", "編集に失敗しました。再度お試しください。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editDiary.jsp");
            dispatcher.forward(request, response);
        }
    }

}
