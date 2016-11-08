package application.models;

import com.googlecode.objectify.annotation.*;

@Entity
public class User 
{
	@Id
	private String Id;
	
	private String username;
	private String password;
 
	public User()
	{
	}
	
	public String getUserId() {
		return this.Id;
	}
 
	public void setUserId(String id) {
		this.Id = id;
	}
 
	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}	
}
