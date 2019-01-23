package fr.petite.annonce.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.petite.annonce.dao.AnnonceDAO;
import fr.petite.annonce.dao.UserDAO;
import fr.petite.annonce.model.Annonce;
import fr.petite.annonce.model.Category;
import fr.petite.annonce.model.UserBean;

/**
 * Servlet implementation class DetailsAnnonceServlet
 */
public class DetailsAnnonceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailsAnnonceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Annonce annonce = AnnonceDAO.findAnnoncesById(id);
		UserBean user=UserDAO.findUser(annonce.getIdCompte());
		
		request.setAttribute("annonceOwner", user);
		request.setAttribute("annonce", annonce);
		// chercher les categories 
		List<Category> categries=AnnonceDAO.findAllCategory();
		request.setAttribute("categories", categries);
		getServletContext().getRequestDispatcher("/details.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
