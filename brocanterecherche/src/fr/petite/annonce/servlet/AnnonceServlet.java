package fr.petite.annonce.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.petite.annonce.dao.AnnonceDAO;
import fr.petite.annonce.dao.UserDAO;
import fr.petite.annonce.model.Annonce;
import fr.petite.annonce.model.Category;
import fr.petite.annonce.model.UserBean;


/**
 * Servlet implementation class AnnonceServlet
 */
public class AnnonceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnonceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//chercher les annonces et les retourner
		 List<Annonce> annonces = AnnonceDAO.findAllAnnonces();
		request.setAttribute("annonces", annonces);
		
		// chercher les categories 
		List<Category> categries=AnnonceDAO.findAllCategory();
		request.setAttribute("categories", categries);
	
		getServletContext().getRequestDispatcher("/annonces.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category cat = new Category();
		cat.setNomCategory(request.getParameter("nomCategorie"));
		AnnonceDAO.addCategory(cat);
		response.sendRedirect("home.jsp");
		
	}

}
