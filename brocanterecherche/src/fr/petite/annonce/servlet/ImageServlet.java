package fr.petite.annonce.servlet;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.petite.annonce.manager.ConnectionManager;
/**
 * Servlet implementation class ImageServlet
 */
@MultipartConfig(maxFileSize = 16177215) // upload file up to 16MB
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // check if the path contains 'grabImage'
	    String id = request.getParameter("id");
	    // read the binary content using the code present in your JSP
	    byte[] data;
		try {
			data = readImage(id);
			response.getOutputStream().write(data);
			
		} catch (SQLException e) {
			  System.out.println("Sorry, could not find that publisher. ");
		}
	}

	private byte[] readImage(String id) throws SQLException {
		Connection connection = ConnectionManager.getConnection();
	            Statement statement = connection.createStatement();
	            ResultSet resultset = 
	                statement.executeQuery("select photo from annonce where annonce_id ="+ id ) ; 
	            if(!resultset.next()) {
	                System.out.println("Sorry, could not find that publisher. ");
	            } else {
	            	Blob blob = resultset.getBlob(1);
	            	int blobLength = (int) blob.length();  
	            	byte[] blobAsBytes = blob.getBytes(1, blobLength);
	            	return blobAsBytes;
	            }
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
