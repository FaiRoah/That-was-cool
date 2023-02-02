package gamesearch.core;

public class User {
	private Integer id;
	private String firstName;
	private String lastName;
	private String password;
	private Boolean is_admin;
	private static Integer loggedInUserId;
	
	
	
	public User(String firstName, String lastName, String password, Boolean is_admin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.is_admin = is_admin;
	}
	
	public User() {
		super();
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIs_admin() {
		return is_admin;
	}
	public void setIs_admin(Boolean is_admin) {
		this.is_admin = is_admin;
	}

	public static Integer getLoggedInUserId() {
		return loggedInUserId;
	}

	public void setLoggedInUserId(Integer loggedInUserId) {
		User.loggedInUserId = loggedInUserId;
	}
	
	
}
