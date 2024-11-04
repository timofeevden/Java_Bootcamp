package edu.school21.spring.service.models;

import java.util.Objects;

public class User {
	private Long id;
	private String email;
	private String password;

	public User() {
		this.id = null;
		this.email = null;
		this.password = null;
	}
	
	public User(Long id, String email) {
		this.id = id;
		this.email = email;
		this.password = null;
	}

	public User(Long id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + "', password='" + password + "'}";
    }
	
}