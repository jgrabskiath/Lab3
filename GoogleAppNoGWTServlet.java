package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.googlecode.objectify.ObjectifyService;

import application.helpers.ServiceResult;
import application.helpers.UserValidator;
import application.models.User;

import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class GoogleAppNoGWTServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
	{		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
//		List<User> toDelete = ofy().load().type(User.class).list();
//		ofy().delete().entities(toDelete).now();
		
		ObjectifyService.register(User.class);
		
		out.println("<head>"
				+ "<style>"
				+ "body {"
				+ "background-image: url('http://modernmixing.com/wp-content/uploads/2016/03/dark-desk-background-dark.jpg');"
				+ "background-repeat: repeat-y;"
				+ "background-size: cover; background-position:center;"
				+ "text-align: center;"
				+ "color: white;"
				+ "}"
				+ ".listHeader"
				+ "{ font-weight: bold; font-size: 24px; color: #cb5904; }"
				+ ".backBtn"
				+ "{ text-decoration: none; border: 1px solid black; background-color: white; color: black; padding: 6px; border-radius: 6px; margin-top: 20px; margin-bottom: 20px }"
				+ "</style>"
				+ "</head>");
		
		out.println("<body>");
		resp.getWriter().println("<div class='listHeader'>List of registered people:</div>" + "<br> <br>");
		
		List<User> allUsers = ofy().load().type(User.class).list();

		int userIndex = 1;
		for(User element : allUsers)
		{
			if(element != null)
			{
				resp.getWriter().println("User " + userIndex + ": " + element.getUsername() + "<br>");
				resp.getWriter().println("Password: " + element.getPassword() + "<br> <br>");
			}
			userIndex++;
		}
		out.println("<a href='/index.html' class='backBtn'>Back to main page</a>");
		out.println("</body>");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		ObjectifyService.register(User.class);
		
		String userName = req.getParameter("username");
		String userPassword = req.getParameter("userpassword");
		
		UserValidator validator = new UserValidator();
		
		ServiceResult validationRes = validator.ValidateUserRecord(userName, userPassword);
		
		if(validationRes.GetValidationResult())
		{
			User user = new User();	
			user.setUserId(UUID.randomUUID().toString());
			user.setUsername(userName);
			user.setPassword(userPassword);
			
			ObjectifyService.ofy().save().entity(user).now();
			
			resp.sendRedirect("/googleappnogwt");
		}
		else
		{
			resp.sendRedirect("/failed.html");
		}	
	}
}
