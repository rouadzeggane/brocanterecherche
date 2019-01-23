package fr.petite.annonce.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import fr.petite.annonce.manager.ConnectionManager;
import fr.petite.annonce.model.UserBean;



public class UserDAO    
{
/**
 * methode qui permet de s'authentifier et d'acceder à notre compte si on en a un
 * @param bean
 * @return
 */
   public static UserBean login(UserBean bean) {
   
      //preparer les objets pour la connexion à la DB
      Statement stmt = null; 
      Connection currentCon = null;
      ResultSet rs = null;
   
      String usermail = bean.getUserMail();   
      String password = bean.getPassword();   
      
      String searchQuery =
            "select * from compte where email='"
                     + usermail
                     + "' AND password='"
                     + password
                     + "'";
      
   System.out.println("Query: "+searchQuery);
      
   try
   {
      //connexion à la base de données
      currentCon = ConnectionManager.getConnection();
      stmt=currentCon.createStatement();
      rs = stmt.executeQuery(searchQuery);
      boolean more = rs.next();
      // if user does not exist set the isValid variable to false
      if (!more)
      {
         System.out.println("Sorry, you are not a registered user! Please sign up first");
         bean.setValid(false);
      }
          
      //if user exists set the isValid variable to true
      else if (more)
      {
         String firstName = rs.getString("prenom");
         String lastName = rs.getString("nom");
         int idCompte = rs.getInt("compte_id");
           
         System.out.println("Welcome " + firstName);
         bean.setFirstName(firstName);
         bean.setLastName(lastName);
         bean.setTel( rs.getString("numeroPhone"));
         bean.setId(idCompte);
         bean.setValid(true);
      }
   }

   catch (Exception ex)
   {
      System.out.println("Log In failed: An Exception has occurred! " + ex);
   }
      
   //fermer les paramètres de connexion
   finally
   {
      closeInstances(stmt, currentCon, rs);
   }

return bean;
   
   }   
   
   
   /**
    * methode qui permet de créer un compte et l'ajouter dans la table Compte de la DB
    * @param bean
    * @return
    */
  public static int  addAccount(UserBean bean) {
 	   
      //preparer les objets pour la connexion à la DB
 	 PreparedStatement statement = null;   
 	 Connection currentCon = null;
     ResultSet rs = null;
     int callBack=0;
    String insertQuery = "INSERT INTO compte(nom, prenom,email, password,numeroPhone) values (? , ?, ?, ?, ?)";

   System.out.println("Query: "+insertQuery);
   try
   {
      //connexion à la DB
      currentCon = ConnectionManager.getConnection();
       statement = currentCon.prepareStatement(insertQuery);
 		statement.setString(1, bean.getLastName());
 		statement.setString(2, bean.getFirstName());
 		statement.setString(3, bean.getUserMail());
 		statement.setString(4,bean.getPassword());
 		statement.setString(5, bean.getTel());
      callBack= statement.executeUpdate();
      // if user does not exist set the isValid variable to false
   }

   catch (Exception ex)
   {
      System.out.println("Log In failed: An Exception has occurred! " + ex);
   }
      
   //fermer les paramètres de connexion à la DB
   finally
   {
	   closeInstances(statement, currentCon, rs);
   }
   
   return callBack;

   }
  
 /**
  * methode qui permet de trouver un utilisateur inscrit à partir du numéro de son compte 
  * @param compteId
  * @return
  */
  
  public static UserBean findUser(Integer compteId) {
	   
      //preparer des objets pour la connexion à la DB
      Statement stmt = null; 
      Connection currentCon = null;
      ResultSet rs = null;
      UserBean bean=new UserBean();
      
      String searchQuery =
            "select * from compte where compte_id="
                     + compteId;
      
   System.out.println("Query: "+searchQuery);
      
   try
   {
      //connexion à la DB
      currentCon = ConnectionManager.getConnection();
      stmt=currentCon.createStatement();
      rs = stmt.executeQuery(searchQuery);
      boolean more = rs.next();
      // if user does not exist set the isValid variable to false
      if (!more)
      {
         System.out.println("Sorry, you are not a registered user! Please sign up first");
         bean.setValid(false);
      }
          
      //if user exists set the isValid variable to true
      else if (more)
      {
         String firstName = rs.getString("prenom");
         String lastName = rs.getString("nom");
         String numeroPhone = rs.getString("numeroPhone");
         int id = rs.getInt("compte_id");
         bean.setFirstName(firstName);
         bean.setLastName(lastName);
         bean.setTel(numeroPhone);
         bean.setId(id);
         bean.setValid(true);
      }
   }

   catch (Exception ex)
   {
      System.out.println("find user failed: An Exception has occurred! " + ex);
   }
      
   // fermer les paramètres de la connexion à la DB
   finally
   {
      closeInstances(stmt, currentCon, rs);
   }

return bean;
   
   }

/**
 * methode qui permet de fermer les paramètres de connexion 
 * @param stmt
 * @param currentCon
 * @param rs
 */
private static void closeInstances(Statement stmt, Connection currentCon,
		ResultSet rs) {
	if (rs != null)   {
         try {
            rs.close();
         } catch (Exception e) {}
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
         } catch (Exception e) {
         }

         currentCon = null;
      }
}   
  

}