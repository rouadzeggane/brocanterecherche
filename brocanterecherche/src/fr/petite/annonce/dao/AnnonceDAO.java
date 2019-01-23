package fr.petite.annonce.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.petite.annonce.manager.ConnectionManager;
import fr.petite.annonce.model.Annonce;
import fr.petite.annonce.model.Category;

public class AnnonceDAO    
{
	
/**
 * *methode qui permet de fermer tous les param�tres de la connexion � la base de donn�es une fois la 
 *requ�te ex�cut�e et cela pour �viter d'avoir des probl�mes en cas de connexions multiples 
 * @param stmt
 * @param currentCon
 * @param rs
 */
	
	
private static void closeInstances(Statement stmt, Connection currentCon,ResultSet rs) {
	if (rs != null)   {
         try {
            rs.close();
         }  catch (Exception e) {}
            rs = null;
     }
   
      if (stmt != null) {
         try {
            stmt.close();
         } catch (Exception e) {}
            stmt = null;
      }
   
      if (currentCon != null) {
         try {
            currentCon.close();
         } catch (Exception e) {}

         currentCon = null;
      }
}   
	/**
	 * methode qui retourne la liste de cat�gories pr�sentes dans la table Category de la base de donn�es
	 * @return
	 */

   public static List<Category> findAllCategory() {
	  //creer une liste de cat�gories  
	  List<Category> categories= new ArrayList<Category>();
      //preparer des objets pour la connexion 
      Statement stmt = null;   
      Connection currentCon = null;
      ResultSet rs = null;    
      //introduire une requ�te qui s�lectionne toutes les cat�gories de la table cat�gorie 
      String searchQuery = "select * from categorie";
      
    System.out.println("Query: "+searchQuery);
      
   try
   {
      //connection � la base de donn�es 
      currentCon = ConnectionManager.getConnection();
      stmt=currentCon.createStatement();
      rs = stmt.executeQuery(searchQuery);
    	  while(rs.next()){
    		    int id = rs.getInt("categorie_id");
    	         String nomCat = rs.getString("nom_categorie");
    	         Category cat= new Category();
    	         cat.setIdCat(id);
    	         cat.setNomCategory(nomCat);
    	         categories.add(cat);
    	         System.out.println("Welcome " + nomCat);
    	  }
   }

   catch (Exception ex)
   {
      System.out.println("Log In failed: An Exception has occurred! " + ex);
   }
      
   //fermer les param�tres de connexion � la base de donn�es 
   finally
   {
      closeInstances(stmt, currentCon, rs);
   }

return categories;
   
}

   
   /**
    *  methode qui permet d'ex�cuter des requ�tes d'insertion d'une nouvelle cat�gorie dans la table Category
    * @param cat
    * @return
    */
  public static int  addCategory(Category cat) {
	   
      //preparer les objets de la connexion � la base de donn�es 
      Statement stmt = null;   
      Connection currentCon = null;
      ResultSet rs = null;    
      int callBack=0;
   
      String insertQuery ="insert into categorie (nom_cat)  values ('"+cat.getNomCategory()+ "')";
    System.out.println("Query: "+insertQuery);
      
   try
   {
      //�tablir une connexion � la base de donn�es
      currentCon = ConnectionManager.getConnection();
      stmt=currentCon.createStatement();
      callBack= stmt.executeUpdate(insertQuery);
   }

   catch (Exception ex)
   {
      System.out.println("Log In failed: An Exception has occurred! " + ex);
   }
      
   //fermer les param�tres de la connexion 
   finally
   {
      closeInstances(stmt, currentCon, rs);
   }
   
   return callBack;

   }   
  
  
  
  /**
   *  m�thode qui permet d'ajouter une nouvelle annonce dans la table Annonce de notre DB
   * @param annonce
   * @return
   */
 public static int  addAnnonce(Annonce annonce) {
	   
     //preparer les param�tres de connexion � la base de donn�es
	 PreparedStatement statement = null; 
	 Connection currentCon = null;
     ResultSet rs = null;    
     int callBack=0;
  
     String insertQuery = "INSERT INTO annonce(titre, description,prix, photo,categorie_id,compte_id) values (?,?,?,?,?,?)";
   System.out.println("Query: "+insertQuery);  
  try
  {
     //connexion � la base de donn�es
     currentCon = ConnectionManager.getConnection();
    	statement = currentCon.prepareStatement(insertQuery);
		statement.setString(1, annonce.getTitre());
		statement.setString(2, annonce.getDescription());
		statement.setDouble(3, annonce.getPrix());
		statement.setBlob(4, annonce.getPhoto());
		statement.setInt(5, annonce.getIdCat());
		statement.setInt(6, annonce.getIdCompte());
    callBack= statement.executeUpdate();
  }

  catch (Exception ex)
  {
     System.out.println("Log In failed: An Exception has occurred! " + ex);
  }
     
  //fermer les param�tres de la connexion � la base de donn�es 
  finally
  {
	  closeInstances(statement, currentCon, rs);
  }
  
  return callBack;

  }
 
/**
 * m�thode qui permet de r�cup�rer la lise de toutes les annonces de la table Annonce de notre DB
 * @return
 */
 public static  List<Annonce> findAllAnnonces() {
	   
	List<Annonce> annonces= new ArrayList<Annonce>();
    //preparer les objets pour la connexion � la base de donn�es 
    Statement stmt = null;  
    Connection currentCon = null;
    ResultSet rs = null;    
    String searchQuery ="select * from annonce";
    
 System.out.println("Query: "+searchQuery);
    
 try
 {
    //connexion � la base de donn�es
    currentCon = ConnectionManager.getConnection();
    stmt=currentCon.createStatement();
    rs = stmt.executeQuery(searchQuery);
  	  while(rs.next()){
  	        Annonce annonce=new Annonce();
  	        annonce.setIdAnnonce(rs.getInt("annonce_id"));
			annonce.setTitre( rs.getString("titre"));
			annonce.setDescription( rs.getString("description"));
			annonce.setPrix(rs.getDouble("prix"));
			annonce.setIdCat( rs.getInt("categorie_id"));
			annonce.setIdCompte(rs.getInt("compte_id"));
  	        annonces.add(annonce);
  	         System.out.println("Welcome " + rs.getString("titre"));
  	  }
 }

 catch (Exception ex)
 {
    System.out.println("Log In failed: An Exception has occurred! " + ex);
 }
 // fermer les param�tres de connexion � la DB apr�s l'ex�cution de la requ�te   
 finally
 {
    closeInstances(stmt, currentCon, rs);
 }

return annonces;
 
 }

 /**
  *  m�thode qui permet de rechercher une annonce selon la cat�gorie et un texte qui apparait dans son titre ou sa 
  * description
  * @param category
  * @param text
  * @return
  */
 public static  List<Annonce> findAnnonces(String category, String text) {
	   
  List<Annonce> annonces= new ArrayList<Annonce>();
  //preparer les objets de la connexion a la DB
  Statement stmt = null;   
  Connection currentCon = null;
  ResultSet rs = null;    

  // requete de recherche avec le texte
  String searchQuery =
	        "select * from annonce where  (titre like '%"+text+"%' or description like '%"+text+"%') ";
  if (category !="" ){
	  searchQuery=searchQuery+ " and categorie_id="+category;
  }
System.out.println("Query: "+searchQuery);
  
try
{
  //connexion a la base de donnees 
  currentCon = ConnectionManager.getConnection();
  
  stmt=currentCon.createStatement();
  
  rs = stmt.executeQuery(searchQuery); 
  
	  while(rs.next()){
	        Annonce annonce=new Annonce();
	        annonce.setIdAnnonce(rs.getInt("annonce_id"));
			annonce.setTitre( rs.getString("titre"));
			annonce.setDescription( rs.getString("description"));
			annonce.setPrix(rs.getDouble("prix"));
			annonce.setIdCompte(rs.getInt("compte_id"));
			//annonce.setIdCat( rs.getInt("categorie_id"));
	        annonces.add(annonce);
	         System.out.println("Welcome " + rs.getString("titre"));
	  }
}

catch (Exception ex)
{
  System.out.println("Log In failed: An Exception has occurred! " + ex);
}
  
// fermer les param�tres de connexion � la DB
finally
{
  closeInstances(stmt, currentCon, rs);
}

return annonces;

}
 
/**
 * m�thode qui permet de trouver une annonce selon l'identifiant
 * @param id
 * @return
 */
 public static  Annonce findAnnoncesById(String id) {
	   
  Annonce annonce=new Annonce();
  //preparer les objets de la connexion � la base de donn�es
  Statement stmt = null;   
  Connection currentCon = null;
  ResultSet rs = null;    
  String searchQuery = "select * from annonce where annonce_id ="+ id ;
System.out.println("Query: "+searchQuery);
  
try
{
  //connexion � la DB
  currentCon = ConnectionManager.getConnection();
  stmt=currentCon.createStatement();
  rs = stmt.executeQuery(searchQuery);
  if(!rs.next()) {
      System.out.println("Sorry, could not find that Annonce. ");
  } else { // compl�ter les attributs de annonce avec les r�sultats correspondant � la requ�te effectu�e
      annonce.setIdAnnonce(rs.getInt("annonce_id"));
		annonce.setTitre( rs.getString("titre"));
		annonce.setDescription( rs.getString("description"));
		annonce.setPrix(rs.getDouble("prix"));
		annonce.setIdCompte(rs.getInt("compte_id"));
		annonce.setIdCat( rs.getInt("categorie_id"));
  }
	
}

catch (Exception ex)
{
  System.out.println("Log In failed: An Exception has occurred! " + ex);
}
  
//fermer les param�tres de connexion � la DB
finally
{
  closeInstances(stmt, currentCon, rs);
}
// l'identifiant est unique donc le r�sultat de la requ�te est au plus une seule annonce
return annonce;

}   
 
 /**
  *  methode qui supprime une annonce en fonction de l'identifiant donn�e en paramm�tre
  * @param id
  * @return
  */
 public static  int deleteAnnoncesById(String id) {
	   
  Statement stmt = null;   
  Connection currentCon = null;
  int callBack=0;
  String deleteQuery = "delete from annonce where annonce_id ="+ id ;
  System.out.println("Query: "+deleteQuery);
  
try
{
  //connexion a la DB
  currentCon = ConnectionManager.getConnection();
  stmt=currentCon.createStatement();
  callBack= stmt.executeUpdate(deleteQuery);
}

catch (Exception ex)
{
  System.out.println("Log In failed: An Exception has occurred! " + ex);
}
  
//fermer les parametres de connexion a la DB
finally
{
  if (stmt != null) {
     try {
        stmt.close();
     } catch (Exception e) {}
        stmt = null;
     }

  if (currentCon != null) {
     try {
        currentCon.close();
     } catch (Exception e) {
     }

     currentCon = null;
  }
}

return callBack;

}

 /**
  * methode qui retourne les annonces que l'utilisateur dont l'identifiant est compteId donn� en argument
  * on l'utilisera pour afficher les annonces que l'utilisateur a lui m�me cr�e
  * @param compteId
  * @return
  */
 
 public static  List<Annonce> findMesAnnonces(int compteId) {
	   
		List<Annonce> annonces= new ArrayList<Annonce>();
	    //preparing some objects for connection
	    Statement stmt = null;  
	    Connection currentCon = null;
	    ResultSet rs = null;    
	    String searchQuery ="select * from annonce where compte_id ="+compteId;
	    
	    
	 System.out.println("Query: "+searchQuery);
	    
	 try
	 {
	    //connexion � la DB
	    currentCon = ConnectionManager.getConnection();
	    stmt=currentCon.createStatement();
	    rs = stmt.executeQuery(searchQuery);
	  	  while(rs.next()){
	  	        Annonce annonce=new Annonce();
	  	        annonce.setIdAnnonce(rs.getInt("annonce_id"));
				annonce.setTitre( rs.getString("titre"));
				annonce.setDescription( rs.getString("description"));
				annonce.setPrix(rs.getDouble("prix"));
				annonce.setIdCat( rs.getInt("categorie_id"));
				annonce.setIdCompte(rs.getInt("compte_id"));
	  	        annonces.add(annonce);
	  	         System.out.println("Mes annonces " + rs.getString("titre"));
	  	  }
	 }

	 catch (Exception ex)
	 {
	    System.out.println("Log In failed: An Exception has occurred! " + ex);
	 }
	    
	 finally
	 {
	    closeInstances(stmt, currentCon, rs);
	 }

	return annonces;
	 
	 }
 
 
}