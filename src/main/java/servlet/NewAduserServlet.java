package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.UserRegistLogic;
import model.UsersBean;

@WebServlet("/NewAduserServlet")
public class NewAduserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public NewAduserServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 新規登録フォーム表示
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

        if ("confirm".equals(action)) {
            // 確認画面からOK押された → DB登録処理
            boolean result = logic.regist(user);
            if(result) {
                request.getRequestDispatcher("/WEB-INF/jsp/newAccountComplete.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMsg", "登録に失敗しました。");
                request.getRequestDispatcher("/WEB-INF/jsp/newAccount.jsp").forward(request, response);
            }
        } else {
            // 新規登録画面から送られてきた場合(確認画面表示)
            String errorMsg = logic.validate(user, request.getParameter("passwordCheck"));
            if(errorMsg != null) {
                request.setAttribute("errorMsg", errorMsg);
                request.getRequestDispatcher("/WEB-INF/jsp/newAccount.jsp").forward(request, response);
                return;
            }
            // エラーなし → 確認画面にパラメータ渡してフォワード
            request.setAttribute("nickname", nickname);
            request.setAttribute("userId", userId);
            request.setAttribute("password", password);
            request.getRequestDispatcher("/WEB-INF/jsp/newAccountComfilm.jsp").forward(request, response);
        }
    }
}

