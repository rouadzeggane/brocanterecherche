package fr.petite.annonce.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.petite.annonce.dao.AnnonceDAO;
import fr.petite.annonce.dao.UserDAO;
import fr.petite.annonce.model.UserBean;

/**
 * Servlet implementation class CreateAnnnonceServlet
 */
public class CreateAccountServlet extends HttpServlet {
	
	
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		response.sendRedirect("creerCompte.jsp"); // logged-in page
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		Map<String, String> erreurs = new HashMap<String, String>();
		UserBean user = new UserBean();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmation = request.getParameter("confirmation");
		String lastName = request.getParameter("nom");
		String firstName = request.getParameter("prenom");
		String tel = request.getParameter("numeroPhone");
		//String idDep = request.getParameter("numDep");
        
        /* Validation des champs du creation de compte */
        
        int row = 0;
        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
            user.setUserMail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setTel(tel);
            row = UserDAO.addAccount(user);
            
    		if (row>0) {
    			UserDAO.login(user);
    			HttpSession session = request.getSession(true);
    			session.setAttribute("currentSessionUser", user);
    			response.sendRedirect("annonces"); // logged-in page
    		}else{
    			erreurs.put("compte","Ce compte existe deja" );
    			request.setAttribute(ATT_ERREURS, erreurs);
    			HttpSession session = request.getSession(true);
    			 RequestDispatcher rd = getServletContext().getRequestDispatcher("/creerCompte.jsp");
            	 rd.forward(request, response);
    		}
        } else {
        	 request.setAttribute( ATT_ERREURS, erreurs );
     		HttpSession session = request.getSession(true);
        	 RequestDispatcher rd = getServletContext().getRequestDispatcher("/creerCompte.jsp");
        	 rd.forward(request, response);
        }
        
	}

	private void validateFormFields(Map<String,String> erreurs, String email, String password,
			String confirmation, String lastName, String firstName, String tel,
			String idDep) {
		/* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            erreurs.put( "email", e.getMessage() );
        }
        
        /* Validation des champs mot de passe et confirmation. */
        try {
            validationMotsDePasse( password, confirmation );
        } catch ( Exception e ) {
            erreurs.put( "password", e.getMessage() );
        }
        
        /* Validation du champ nom. */
        try {
            validationNom( lastName );
        } catch ( Exception e ) {
            erreurs.put( "nom", e.getMessage() );
        }
        
        /* Validation du champ prenom. */
        try {
            validationNom( firstName );
        } catch ( Exception e ) {
            erreurs.put( "prenom", e.getMessage() );
        }
        
        /* Validation du champ telephone. */
        try {
            validationNom( tel );
        } catch ( Exception e ) {
            erreurs.put( "numeroPhone", e.getMessage() );
        }
        
        /* Validation du champ departement. */
        try {
        //	validationDep( idDep );
        } catch ( Exception e ) {
        	erreurs.put( "numDep", e.getMessage() );
        }
	}
	
	/**
	 * Valide l'adresse mail saisie.
	 */
	private void validationEmail( String email ) throws Exception {
	    if ( email != null && email.trim().length() != 0 ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    } else {
	        throw new Exception( "Merci de saisir une adresse mail." );
	    }
	}
	
	
	/**
	 * Valide les mots de passe saisis.
	 */
	private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
	    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
	        if (!motDePasse.equals(confirmation)) {
	            throw new Exception("Les mots de passe entr�s sont diff�rents, merci de les saisir � nouveau.");
	        } else if (motDePasse.trim().length() < 3) {
	            throw new Exception("Les mots de passe doivent contenir au moins 3 caract�res.");
	        }
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}
	
	/**
	 * Valide le nom d'utilisateur saisi.
	 */
	private void validationNom( String nom ) throws Exception {
	    if ( nom != null && nom.trim().length() < 3 ) {
	        throw new Exception( "Le nom,Prenom ou telephone d'utilisateur doivent contenir au moins 3 caract�res." );
	    }
	}
	
	
	
	

}
