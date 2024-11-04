package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.List;
import java.util.LinkedList;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
	private static final String QUERY_USER_FIND_BY_ID = "SELECT * FROM users WHERE id = ?;";
	private static final String QUERY_USER_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?;";
	private static final String QUERY_USER_FIND_ALL = "SELECT * FROM users;";
	private static final String QUERY_USER_SAVE = "INSERT INTO users(id, email) VALUES (?, ?);";
	private static final String QUERY_USER_UPDATE = "UPDATE users SET email = ? WHERE id = ?;";
	private static final String QUERY_USER_DELETE = "DELETE FROM users WHERE id = ?;";
	
    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
    public Optional<User> findById(Long id) {
		User foundUser = jdbcTemplate.query(QUERY_USER_FIND_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
			.stream().findFirst().orElse(null);
        return Optional.of(foundUser);
    }
	
	@Override
    public List<User> findAll() {
        return jdbcTemplate.query(QUERY_USER_FIND_ALL, new BeanPropertyRowMapper<>(User.class));
    }
	
	
	@Override
    public void save(User entity) {
        if (jdbcTemplate.update(QUERY_USER_SAVE, entity.getId(), entity.getEmail()) == 0) {
			System.err.println("Can't save User with id = " + entity.getId());
		}
    }
	
	@Override
    public void update(User entity) {
        if (jdbcTemplate.update(QUERY_USER_UPDATE, entity.getEmail(), entity.getId()) == 0) {
            System.err.println("Can't update User with id = " + entity.getId());
        }
    }
	
	@Override
    public void delete(Long id) {
        if (jdbcTemplate.update(QUERY_USER_DELETE, id) == 0) {
            System.err.println("Cant't delete/find User with id = " + id);
        }
    }
	
	@Override
    public Optional<User> findByEmail(String email) {
        User foundUser = jdbcTemplate.query(QUERY_USER_FIND_BY_EMAIL, new Object[]{email}, new BeanPropertyRowMapper<>(User.class))
			.stream().findFirst().orElse(null);
		return Optional.of(foundUser);
    }

}