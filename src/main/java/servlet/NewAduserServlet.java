package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logic.UserRegistLogic;
import model.UsersBean;

@WebServlet("/NewAduserServlet")
public class NewAduserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public NewAduserServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションから一時ユーザー情報を取得（戻る場合用）
        HttpSession session = request.getSession(false);
        if(session != null) {
            UsersBean tempUser = (UsersBean) session.getAttribute("tempUser");
            if(tempUser != null) {
                request.setAttribute("user", tempUser);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/newAccount.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nickname = request.getParameter("nickname");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String action = request.getParameter("action"); 

        UsersBean user = new UsersBean();
        user.setNickname(nickname);
        user.setUserId(userId);
        user.setPassword(password);

        UserRegistLogic logic = new UserRegistLogic();
        HttpSession session = request.getSession();

        if ("confirm".equals(action)) {
            // 確認画面でOK → DB登録
            boolean result = logic.regist(user);
            if(result) {
                session.removeAttribute("tempUser"); // 登録完了したらセッション削除
                request.getRequestDispatcher("/WEB-INF/jsp/newAccountComplete.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMsg", "登録に失敗しました。");
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/jsp/newAccount.jsp").forward(request, response);
            }
        } else {
            // 新規登録画面から送信 → 確認画面
            String errorMsg = logic.validate(user, request.getParameter("passwordCheck"));
            if(errorMsg != null) {
                request.setAttribute("errorMsg", errorMsg);
                request.setAttribute("user", user); // エラー時も値を保持
                request.getRequestDispatcher("/WEB-INF/jsp/newAccount.jsp").forward(request, response);
                return;
            }

            // エラーなし → 確認画面に渡す
            session.setAttribute("tempUser", user); // セッションに一時保存
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/jsp/newAccountComfilm.jsp").forward(request, response);
        }
    }
}
