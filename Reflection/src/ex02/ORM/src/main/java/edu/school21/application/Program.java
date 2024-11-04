package edu.school21.application;

import edu.school21.manager.OrmManager;
import edu.school21.models.User;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/JavaDay07");
        config.setUsername("postgres");
        config.setPassword("1");	
		DataSource dataSource = new HikariDataSource(config);
        OrmManager ormManager = new OrmManager(dataSource);

        User newUser = new User(null, "Rayan", "Gosling", 30);
        System.out.println("Save new user:");
        ormManager.save(newUser);

        System.out.println("Find user by ID:");
        User foundUser = ormManager.findById(1L, User.class);
        if (foundUser != null) {
            System.out.println("Found User: " + foundUser);
            System.out.println("Update user to Jhon Smith:");
            foundUser.setFirstName("Jhon");
            foundUser.setLastName("Smith");
            foundUser.setAge(21);
            ormManager.update(foundUser);
            System.out.println("Find updated user:");
            User updatedUser = ormManager.findById(1L, User.class);
            if (updatedUser != null) {
                System.out.println("Updated User: " + updatedUser);
            }
        } else {
            System.out.println("User not found!");
        }
        System.out.println("Find user with bad ID:");
        User badIdUser = ormManager.findById(15L, User.class);
        System.out.println("Bad ID User: " + badIdUser);
    }
}