package edu.school21.models;

import java.util.Objects;

public class User {
    private Long identifier;
    private String login;
    private String password;
    private Boolean authenticationStatus;

    public User(Long identifier, String login, String password, Boolean authenticationStatus) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.authenticationStatus = authenticationStatus;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(Boolean authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
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
        return identifier.equals(user.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return new String("User: {" + "id=" + identifier + 
			", login='" + login + 
			", password=" + password + 
			", authenticationStatus=" + authenticationStatus + "}");
    }
}