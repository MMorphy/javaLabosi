package hr.java.web.petkovic.moneyapp.trosak;

public class Auth {
	String user;
	String role;

	public Auth() 
	{
	}
	public Auth(String user, String role)
	{
		this.user = user;
		this.role=role;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
