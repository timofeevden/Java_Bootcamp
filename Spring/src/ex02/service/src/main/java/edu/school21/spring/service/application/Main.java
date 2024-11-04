package edu.school21.spring.service.application;

import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UsersRepository;
import edu.school21.spring.service.services.UsersService;
import edu.school21.spring.service.config.ApplicationConfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        createTable();
        UsersRepository usersRepositoryJdbc = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        UsersRepository usersRepositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
		//	New users
		User user1 = new User(1L, "Ivan@mail.ru - Jdbc", "12345");
		User user2 = new User(2L, "Vano@mail.ru - JdbcTemplate", "qwerty");
		usersRepositoryJdbc.save(user1);		
		usersRepositoryJdbcTemplate.save(user2);
		System.out.println("\nUser saved by Jdbc - " + usersRepositoryJdbc.findById(1L));
		System.out.println("User saved by JdbcTemplate - " + usersRepositoryJdbc.findById(2L));
		//	Update users
		user1.setEmail("NEWIvan@mail.ru - Jdbc");
		user2.setEmail("NEWVANO@mail.ru - JdbcTemplate");
		usersRepositoryJdbc.update(user1);		
		usersRepositoryJdbcTemplate.update(user2);
		System.out.println("\nUser updated by Jdbc - " + usersRepositoryJdbc.findById(1L));
		System.out.println("User updated by JdbcTemplate - " + usersRepositoryJdbc.findById(2L));
		//	Find by email
		System.out.println("\nFound User by email by Jdbc - " + usersRepositoryJdbc.findByEmail("NEWIvan@mail.ru - Jdbc"));
		System.out.println("Found User by email by  - JdbcTemplate" + usersRepositoryJdbc.findByEmail("NEWVANO@mail.ru - JdbcTemplate"));
		//	Add new and delete old users
		usersRepositoryJdbc.save(new User(3L, "test Jdbc"));		
		usersRepositoryJdbcTemplate.save(new User(4L, "test JdbcTemplate"));
		usersRepositoryJdbc.delete(1L);		
		usersRepositoryJdbcTemplate.delete(2L);
		//	Show all users
		List<User> listByJdbc = usersRepositoryJdbc.findAll();
		System.out.println("\nAll users by Jdbc after delete:");
		for(User user : listByJdbc) {
			System.out.println(user);
		}
		List<User> listByJdbcTemplate = usersRepositoryJdbcTemplate.findAll();
		System.out.println("\nAll users by JdbcTemplate after delete:");
		for(User user : listByJdbcTemplate) {
			System.out.println(user);
		}
		//	Find with bad id
		System.out.println("\nTry find user with id = 1 by Jdbc: " + usersRepositoryJdbc.findById(1L));
		System.out.println("Try find user with id = 1 JdbcTemplate: " + usersRepositoryJdbc.findById(1L));


		UsersService service = context.getBean("userService", UsersService.class);
        System.out.println("\nNew user and password:" + service.signUp("sign_in"));
	}

    private static void createTable() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:postgresql://localhost:5432/Day08");
        config.setUsername("postgres");
        config.setPassword("1");
		DataSource dataSource = new HikariDataSource(config);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(id BIGSERIAL PRIMARY KEY, email VARCHAR(50), password VARCHAR(50));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
	
	
}