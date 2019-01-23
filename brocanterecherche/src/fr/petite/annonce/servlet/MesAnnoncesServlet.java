package fr.petite.annonce.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.petite.annonce.dao.AnnonceDAO;
import fr.petite.annonce.model.Annonce;
import fr.petite.annonce.model.Category;
import fr.petite.annonce.model.UserBean;

/**
 * Servlet implementation class MesAnnoncesServlet
 */
public class MesAnnoncesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MesAnnoncesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// check user if is logged
		HttpSession session = request.getSession(true);
		if(session.getAttribute("currentSessionUser")!=null){
			//recuperer id de utilisateur connecte
			int id =((UserBean)session.getAttribute("currentSessionUser")).getId();
			//chercher les annonces et les retourner
			 List<Annonce> mesAnnonces = AnnonceDAO.findMesAnnonces(id);
			request.setAttribute("mesAnnonces", mesAnnonces);
			
			
		}
		
		// chercher les categories 
		List<Category> categries=AnnonceDAO.findAllCategory();
		request.setAttribute("categories", categries);
		

		
		
		getServletContext().getRequestDispatcher("/mesAnnonces.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
