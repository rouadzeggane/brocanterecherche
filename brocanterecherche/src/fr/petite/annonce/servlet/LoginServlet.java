package fr.petite.annonce.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.petite.annonce.dao.UserDAO;
import fr.petite.annonce.model.UserBean;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 2562294252731783855L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			java.io.IOException {

		try {

			UserBean user = new UserBean();
			user.setUserMail(request.getParameter("email"));
			user.setPassword(request.getParameter("password"));
			user = UserDAO.login(user);

			if (user.isValid()) {

				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
				response.sendRedirect("annonces"); // logged-in page
			}
			else{
				request.setAttribute("message", "error ");
				response.sendRedirect("login.jsp"); // error page
			}
		}

		catch (Throwable theException) {
			System.out.println(theException);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("login.jsp"); // logged-in page
	}
}
