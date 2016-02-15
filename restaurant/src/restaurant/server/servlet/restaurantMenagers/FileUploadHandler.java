package restaurant.server.servlet.restaurantMenagers;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import restaurant.externals.HashPassword;
import restaurant.server.session.ImageDaoLocal;


public class FileUploadHandler extends HttpServlet{

	private static final long serialVersionUID = 7632788603673463611L;
	
    @EJB
    private ImageDaoLocal imageDao;
    
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //process only if its multipart content
        if(ServletFileUpload.isMultipartContent(request)){
            try {
            	DiskFileItemFactory dfif = new DiskFileItemFactory();
            	ServletFileUpload sfu = new ServletFileUpload(dfif);
                List<FileItem> multiparts = sfu.parseRequest(request);
              
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                    	if(isImage(item)){
	                        String name = new File(item.getName()).getName();
	                        byte[] salt = new byte[16];
	                        byte[] hashedName = new byte[80];
	                        salt = HashPassword.getNextSalt();
	                        char[] charName = HashPassword.strToChar(name);
	                        hashedName = HashPassword.hashPassword(charName, salt);
	                        String path = getServletContext().getRealPath(".")+"/images" + File.separator + hashedName;
	                        File file = new File(path);
	                        item.write( file);
	                        request.getSession().setAttribute("uploadImageRealName", name);
	                        request.getSession().setAttribute("uploadImageHashedName", hashedName);
	                        request.getSession().setAttribute("uploadImagePath", path);
                    	}else{
            				System.out.println("Nije odgovarajuci tip.");
            				response.sendRedirect(response.encodeRedirectURL("../../system-menager/restaurant-menager/createRestaurantMenager.jsp"));
            				return;
                    	}
                    }
                }
                

            } catch (Exception ex) {
				System.out.println("Nije odgovarajuci tip.");
				response.sendRedirect(response.encodeRedirectURL("../../system-menager/restaurant-menager/createRestaurantMenager.jsp"));
				return;
            }          
         
        }else{
			System.out.println("Nije odgovarajuci tip.");
			response.sendRedirect(response.encodeRedirectURL("../../system-menager/restaurant-menager/createRestaurantMenager.jsp"));
			return;
        }
        
		System.out.println("Uspeh!");
		response.sendRedirect(response.encodeRedirectURL("../../system-menager/restaurant-menager/createRestaurantMenager.jsp"));
		return;
     
    }
    
    private boolean isImage(FileItem item){
        String mimetype= item.getContentType();
        String type = mimetype.split("/")[0];
        if(type.equals("image"))
            return true;
        else 
            return false;
    }
}
