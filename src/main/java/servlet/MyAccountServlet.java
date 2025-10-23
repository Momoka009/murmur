// AccountServlet.java
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MyAccountServlet")
public class MyAccountServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            // ログインしてなければログイン画面へリダイレクト
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        // フォワードしてJSP表示
        request.getRequestDispatcher("/WEB-INF/jsp/myAccount.jsp").forward(request, response);
    }
}
