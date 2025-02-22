package edu.school21.spring.service.models;

public class User {
	private Long id;
	private String email;

	public User() {
		this.id = null;
		this.email = null;
	}
	
	public User(Long id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + "\'}";
    }
	
}