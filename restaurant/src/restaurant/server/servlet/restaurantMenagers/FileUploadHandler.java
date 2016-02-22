package restaurant.server.servlet.restaurantMenagers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.externals.HashPassword;
import restaurant.externals.ResultCode;
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
		ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=utf-8");
        
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
	                        String path = getServletContext().getContextPath()+"/images" + File.separator + hashedName;
	                        File file = new File(getServletContext().getRealPath("")+"/images" + File.separator + hashedName);
	                        try{
	                        	item.write( file);
	                        }catch(Exception ex){
	                        	System.out.println(ex.getMessage());
	                        }
	                        request.getSession().setAttribute("uploadImageRealName", name);
	                        request.getSession().setAttribute("uploadImageHashedName", hashedName);
	                        request.getSession().setAttribute("uploadImagePath", path);
                    	}else{
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            PrintWriter out = response.getWriter();
                            mapper.writeValue(out, ResultCode.REGISTER_USER_FILE_UPLOAD_SUCC.toString());
                    		return;
                    	}
                    }
                }
                

            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                PrintWriter out = response.getWriter();
                mapper.writeValue(out, ResultCode.REGISTER_USER_FILE_UPLOAD_SUCC.toString());
        		return;
            }          
         
        }else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            mapper.writeValue(out, ResultCode.REGISTER_USER_FILE_UPLOAD_SUCC.toString());
    		return;
        }
        
        if(request.getSession().getAttribute("updateRestaurantMenager") == null){
	        response.setStatus(HttpServletResponse.SC_OK);
	        PrintWriter out = response.getWriter();
	        if(request.getSession().getAttribute("updateRestaurantMenager") == null)
	        mapper.writeValue(out, ResultCode.REGISTER_USER_FILE_UPLOAD_SUCC.toString());
			return;
        }else{
	        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
	        PrintWriter out = response.getWriter();
	        if(request.getSession().getAttribute("updateRestaurantMenager") == null)
	        mapper.writeValue(out, ResultCode.REGISTER_USER_FILE_UPLOAD_SUCC.toString());
			return;       	
        }
     
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
