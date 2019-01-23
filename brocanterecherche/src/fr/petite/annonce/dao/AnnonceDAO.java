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
 * *methode qui permet de fermer tous les paramétres de la connexion à la base de données une fois la 
 *requéte exécutée et cela pour éviter d'avoir des problémes en cas de connexions multiples 
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
	 * methode qui retourne la liste de catégories présentes dans la table Category de la base de données
	 * @return
	 */

   public static List<Category> findAllCategory() {
	  //creer une liste de catégories  
	  List<Category> categories= new ArrayList<Category>();
      //preparer des objets pour la connexion 
      Statement stmt = null;   
      Connection currentCon = null;
      ResultSet rs = null;    
      //introduire une requéte qui sélectionne toutes les catégories de la table catégorie 
      String searchQuery = "select * from categorie";
      
    System.out.println("Query: "+searchQuery);
      
   try
   {
      //connection à la base de données 
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
      
   //fermer les paramètres de connexion à la base de données 
   finally
   {
      closeInstances(stmt, currentCon, rs);
   }

return categories;
   
}

   
   /**
    *  methode qui permet d'exécuter des requétes d'insertion d'une nouvelle catégorie dans la table Category
    * @param cat
    * @return
    */
  public static int  addCategory(Category cat) {
	   
      //preparer les objets de la connexion à la base de données 
      Statement stmt = null;   
      Connection currentCon = null;
      ResultSet rs = null;    
      int callBack=0;
   
      String insertQuery ="insert into categorie (nom_cat)  values ('"+cat.getNomCategory()+ "')";
    System.out.println("Query: "+insertQuery);
      
   try
   {
      //établir une connexion à la base de données
      currentCon = ConnectionManager.getConnection();
      stmt=currentCon.createStatement();
      callBack= stmt.executeUpdate(insertQuery);
   }

   catch (Exception ex)
   {
      System.out.println("Log In failed: An Exception has occurred! " + ex);
   }
      
   //fermer les paramétres de la connexion 
   finally
   {
      closeInstances(stmt, currentCon, rs);
   }
   
   return callBack;

   }   
  
  
  
  /**
   *  méthode qui permet d'ajouter une nouvelle annonce dans la table Annonce de notre DB
   * @param annonce
   * @return
   */
 public static int  addAnnonce(Annonce annonce) {
	   
     //preparer les paramétres de connexion à la base de données
	 PreparedStatement statement = null; 
	 Connection currentCon = null;
     ResultSet rs = null;    
     int callBack=0;
  
     String insertQuery = "INSERT INTO annonce(titre, description,prix, photo,categorie_id,compte_id) values (?,?,?,?,?,?)";
   System.out.println("Query: "+insertQuery);  
  try
  {
     //connexion à la base de données
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
     
  //fermer les paramétres de la connexion à la base de données 
  finally
  {
	  closeInstances(statement, currentCon, rs);
  }
  
  return callBack;

  }
 
/**
 * méthode qui permet de récupérer la lise de toutes les annonces de la table Annonce de notre DB
 * @return
 */
 public static  List<Annonce> findAllAnnonces() {
	   
	List<Annonce> annonces= new ArrayList<Annonce>();
    //preparer les objets pour la connexion à la base de données 
    Statement stmt = null;  
    Connection currentCon = null;
    ResultSet rs = null;    
    String searchQuery ="select * from annonce";
    
 System.out.println("Query: "+searchQuery);
    
 try
 {
    //connexion à la base de données
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
 // fermer les paramétres de connexion à la DB aprés l'exécution de la requéte   
 finally
 {
    closeInstances(stmt, currentCon, rs);
 }

return annonces;
 
 }

 /**
  *  méthode qui permet de rechercher une annonce selon la catégorie et un texte qui apparait dans son titre ou sa 
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
  
// fermer les paramétres de connexion à la DB
finally
{
  closeInstances(stmt, currentCon, rs);
}

return annonces;

}
 
/**
 * méthode qui permet de trouver une annonce selon l'identifiant
 * @param id
 * @return
 */
 public static  Annonce findAnnoncesById(String id) {
	   
  Annonce annonce=new Annonce();
  //preparer les objets de la connexion à la base de données
  Statement stmt = null;   
  Connection currentCon = null;
  ResultSet rs = null;    
  String searchQuery = "select * from annonce where annonce_id ="+ id ;
System.out.println("Query: "+searchQuery);
  
try
{
  //connexion à la DB
  currentCon = ConnectionManager.getConnection();
  stmt=currentCon.createStatement();
  rs = stmt.executeQuery(searchQuery);
  if(!rs.next()) {
      System.out.println("Sorry, could not find that Annonce. ");
  } else { // compléter les attributs de annonce avec les résultats correspondant à la requéte effectuée
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
  
//fermer les paramétres de connexion à la DB
finally
{
  closeInstances(stmt, currentCon, rs);
}
// l'identifiant est unique donc le résultat de la requéte est au plus une seule annonce
return annonce;

}   
 
 /**
  *  methode qui supprime une annonce en fonction de l'identifiant donnée en parammétre
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
  * methode qui retourne les annonces que l'utilisateur dont l'identifiant est compteId donnï¿½ en argument
  * on l'utilisera pour afficher les annonces que l'utilisateur a lui mï¿½me crï¿½e
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
	    //connexion ï¿½ la DB
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