package fr.petite.annonce.manager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class ConnectionManager {

   static Connection con;
   static String url;
         
   public static Connection getConnection()
   {
     
     
       //  String url ="jdbc:mysql://localhost:3306/PFE_PETITES_ANNONCES";

        // Class.forName("com.mysql.jdbc.Driver");
         
         try
         {      
        	  Properties prop = new Properties();
              InputStream inputStream = ConnectionManager.class.getClassLoader().getResourceAsStream("/db.properties");
              prop.load(inputStream);
              String driver = prop.getProperty("driver");
              String url = prop.getProperty("url");
              String user = prop.getProperty("user");
              String password = prop.getProperty("password");
              Class.forName(driver);
            //con = DriverManager.getConnection(url,"root","Mahiou641992");
              con = DriverManager.getConnection(url,user,password);
                                     
         }
         
         catch (Exception ex)
         {
            ex.printStackTrace();
         }
     
   return con;
}
   
   
   private static Connection connection = null;

   public static Connection getConnections() {
       if (connection != null)
           return connection;
       else {
           try {
               Properties prop = new Properties();
               InputStream inputStream = ConnectionManager.class.getClassLoader().getResourceAsStream("/db.properties");
               prop.load(inputStream);
               String driver = prop.getProperty("driver");
               String url = prop.getProperty("url");
               String user = prop.getProperty("user");
               String password = prop.getProperty("password");
               Class.forName(driver);
               connection = DriverManager.getConnection(url, user, password);
           } catch (ClassNotFoundException e) {
              // e.printStackTrace();
        	   System.out.println(e);
           } catch (SQLException e) {
             //  e.printStackTrace();
        	   System.out.println(e);
           } catch (FileNotFoundException e) {
              // e.printStackTrace();
        	   System.out.println(e);
           } catch (IOException e) {
               //e.printStackTrace();
        	   System.out.println(e);
           }
           return connection;
       }

   }
   
   
   
}