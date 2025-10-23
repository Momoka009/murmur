package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.LoginLogic;
import model.UsersBean;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		    String userId = request.getParameter("userId");
		    String password = request.getParameter("password");

		    LoginLogic loginLogic = new LoginLogic();
		    boolean loginResult = loginLogic.execute(userId, password);

		    if (loginResult) {
		        UsersBean user = loginLogic.getUser(userId);  // 認証成功したユーザ情報を取得
		        request.getSession().setAttribute("loginUser", user);
		        response.sendRedirect("MyDiaryServlet");
		    } else {
		        request.setAttribute("errorMsg", "ユーザIDかパスワードが違います");
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		        dispatcher.forward(request, response);
		    }
		}

}
