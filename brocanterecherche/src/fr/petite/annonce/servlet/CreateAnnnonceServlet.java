package fr.petite.annonce.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.petite.annonce.dao.AnnonceDAO;
import fr.petite.annonce.dao.UserDAO;
import fr.petite.annonce.model.Annonce;
import fr.petite.annonce.model.Category;
import fr.petite.annonce.model.UserBean;

/**
 * Servlet implementation class CreateAnnnonceServlet
 */
@MultipartConfig(maxFileSize = 16177215) // upload file up to 16MB
public class CreateAnnnonceServlet extends HttpServlet {
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAnnnonceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categries=AnnonceDAO.findAllCategory();
		HttpSession session = request.getSession(true);
		request.setAttribute("categories", categries);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/createAnnonce.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String message = null; // message will be sent back to client

			
			Map<String, String> erreurs = new HashMap<String, String>();
try{
			// gets values of text fields
			String categorie_id = request.getParameter("categorie_id");
			String titre = request.getParameter("titre");
			String description = request.getParameter("description");
			String prix = request.getParameter("prix");

			InputStream inputStream = null;
			// obtains the upload file part in this multipart request
			Part filePart = request.getPart("photo");
			if (filePart != null) {
				// debug messages
				System.out.println(filePart.getName());
				System.out.println(filePart.getSize());
				System.out.println(filePart.getContentType());

				// obtains input stream of the upload file
				inputStream = filePart.getInputStream();
			}
			
			/* Validation des champs du creation de compte */
			validateFormFields(erreurs,titre ,description, prix);
			
			int row = 0;
	        /* Initialisation du rÃ©sultat global de la validation. */
	   if ( erreurs.isEmpty() ) {
			
			Annonce annonce=new Annonce();
			annonce.setTitre(titre);
			annonce.setDescription(description);
			annonce.setPhoto(inputStream);
			annonce.setPrix(Double.parseDouble(prix));
			annonce.setIdCat(Integer.parseInt(categorie_id));
			// check user if is logged
			HttpSession session = request.getSession(true);
			if(session.getAttribute("currentSessionUser")!=null){
				
				UserBean user = (UserBean) session.getAttribute("currentSessionUser");
				user.setUserMail(request.getParameter("email"));
				user.setPassword(request.getParameter("password"));
				user = UserDAO.login(user);
				annonce.setIdCompte(user.getId());
				
			}
			row =AnnonceDAO.addAnnonce(annonce);
			// sends the statement to the database server
			if (row > 0) {
				message = "Image is uploaded successfully into the Database";
				request.setAttribute("Message", message);
				response.sendRedirect("annonces");
			}
			else {
    			request.setAttribute(ATT_ERREURS, erreurs);
    			List<Category> categries=AnnonceDAO.findAllCategory();
    			request.setAttribute("categories", categries);
   			 	RequestDispatcher rd = getServletContext().getRequestDispatcher("/createAnnonce.jsp");
   			 	rd.forward(request, response);  			 	
   			 	
			}
			
       } else {
   		HttpSession session = request.getSession(true);
      	 request.setAttribute( ATT_ERREURS, erreurs );
			List<Category> categries=AnnonceDAO.findAllCategory();
			request.setAttribute("categories", categries);
      	 RequestDispatcher rd = getServletContext().getRequestDispatcher("/createAnnonce.jsp");
      	 rd.forward(request, response);
      }
} catch (Exception ex) {
		message = "ERROR; " + ex.getMessage();
		ex.printStackTrace();
}

	}
	
	
	private void validateFormFields(Map<String,String> erreurs, String titre, String description,
			String prix) {
		/* Validation du champ titre. */

        try {
            validationString( titre );
        } catch ( Exception e ) {
            erreurs.put( "titre", e.getMessage() );
        }
        
        /* Validation du champ description. */
        try {
            validationString( description );
        } catch ( Exception e ) {
            erreurs.put( "description", e.getMessage() );
        }
        
        /* Validation du champ prix. */
        try {
            validationPrix( prix );
        } catch ( Exception e ) {
            erreurs.put( "prix", e.getMessage() );
        }
       
	}
	
	
	/**
	 * Valide le titre ou la description de l'annonce saisi.
	 */
	private void validationString( String nom ) throws Exception {
	    if ( nom != null && nom.trim().length() < 3 ) {
	        throw new Exception( "Le titre ou la description de l'annonce doivent contenir au moins 3 caractéres." );
	    }
	}
	
	/**
	 * Valide le prix de l'annonce saisi.
	 */
	private void validationPrix( String prix ) throws Exception {
	    
	    	try{
	    		Double.parseDouble(prix);
	    	}catch (Exception e){
	    		throw new Exception( "vous devez remplir le champs prix" );
	    		
	    	}
	    	
	    	if (Double.parseDouble(prix) < 0 ){
		        throw new Exception( "le prix ne doit pas etre en negatif" );
		    }
	  
	}
	
	
	
	

}
