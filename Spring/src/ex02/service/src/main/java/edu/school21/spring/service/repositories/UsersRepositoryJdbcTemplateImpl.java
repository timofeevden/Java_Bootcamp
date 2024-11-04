package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.List;
import java.util.LinkedList;

@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
	private static final String QUERY_FIND_BY_ID = "SELECT * FROM users WHERE id = ?;";
	private static final String QUERY_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?;";
	private static final String QUERY_FIND_ALL = "SELECT * FROM users;";
	private static final String QUERY_SAVE = "INSERT INTO users(id, email, password) VALUES(?, ?, ?);";
	private static final String QUERY_UPDATE = "UPDATE users SET email = ?, password = ? WHERE id = ?;";
	private static final String QUERY_DELETE = "DELETE FROM users WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
    public Optional<User> findById(Long id) {
		User foundUser = jdbcTemplate.query(QUERY_FIND_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
			.stream().findFirst().orElse(null);
        return Optional.of(foundUser);
    }
	
	@Override
    public List<User> findAll() {
        return jdbcTemplate.query(QUERY_FIND_ALL, new BeanPropertyRowMapper<>(User.class));
    }
	
	@Override
    public void save(User entity) {
        if (jdbcTemplate.update(QUERY_SAVE, entity.getId(), entity.getEmail(), entity.getPassword()) == 0) {
			System.err.println("Can't save User with id = " + entity.getId());
		}
    }
	
	@Override
    public void update(User entity) {
        if (jdbcTemplate.update(QUERY_UPDATE, entity.getEmail(), entity.getPassword(), entity.getId()) == 0) {
            System.err.println("Can't update User with id = " + entity.getId());
        }
    }
	
	@Override
    public void delete(Long id) {
        if (jdbcTemplate.update(QUERY_DELETE, id) == 0) {
            System.err.println("Cant't delete/find User with id = " + id);
        }
    }
	
	@Override
    public Optional<User> findByEmail(String email) {
        User foundUser = jdbcTemplate.query(QUERY_FIND_BY_EMAIL, new Object[]{email}, new BeanPropertyRowMapper<>(User.class))
			.stream().findFirst().orElse(null);
		return Optional.of(foundUser);
    }

}