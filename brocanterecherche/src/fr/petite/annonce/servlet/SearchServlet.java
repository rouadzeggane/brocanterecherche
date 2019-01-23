package fr.petite.annonce.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.petite.annonce.dao.AnnonceDAO;
import fr.petite.annonce.model.Annonce;
import fr.petite.annonce.model.Category;
//import fr.petite.annonce.model.Departement;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categorie_id = request.getParameter("nomCategorie");
		System.out.println("id cat : "+categorie_id);
		String search = request.getParameter("searchInput");
		List<Annonce> annonces = AnnonceDAO.findAnnonces(categorie_id,search);
		request.setAttribute("annonces", annonces);
		
		// chercher les categories 
		List<Category> categries=AnnonceDAO.findAllCategory();
		request.setAttribute("categories", categries);
		
		
		getServletContext().getRequestDispatcher("/annonces.jsp").forward(request, response);
	}

}
