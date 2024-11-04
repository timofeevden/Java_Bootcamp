package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.List;
import java.util.LinkedList;

public class UsersRepositoryJdbcImpl implements UsersRepository {
	private static final String QUERY_USER_FIND_BY_ID = "SELECT * FROM users WHERE id = ?;";
	private static final String QUERY_USER_FIND_BY_EMAIL = "SELECT * FROM users WHERE email = ?;";
	private static final String QUERY_USER_FIND_ALL = "SELECT * FROM users;";
	private static final String QUERY_USER_SAVE = "INSERT INTO users(id, email) VALUES (?, ?);";
	private static final String QUERY_USER_UPDATE = "UPDATE users SET email = ? WHERE id = ?;";
	private static final String QUERY_USER_DELETE = "DELETE FROM users WHERE id = ?;";
	
	private final DataSource dataSource;
	private Connection connection;
	
    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
		try {
			connection = this.dataSource.getConnection();
		} catch (SQLException e) {
			connection = null;
			e.printStackTrace();
		}
    }
	
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private boolean getConnection() {
		if (connection != null) {
			try {
				if (connection.isClosed()) {
					connection = dataSource.getConnection();
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();				
			}
		}
		return false;
	}
	
	@Override
    public Optional<User> findById(Long id) {
		if (getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_FIND_BY_ID)) {
				preparedStatement.setLong(1, id);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					User foundUser = new User(resultSet.getLong(1), resultSet.getString(2));
					return Optional.of(foundUser);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return Optional.empty();
    }
	
	@Override
    public List<User> findAll() {
        List<User> allUsers = new LinkedList<>();
		if (getConnection()) {
			try (Statement statement = connection.createStatement()) {
				ResultSet resultSet = statement.executeQuery(QUERY_USER_FIND_ALL);
				while (resultSet.next()) {
					User user = new User(resultSet.getLong(1), resultSet.getString(2));
					allUsers.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return allUsers;
    }
	
	@Override
    public void save(User entity) {
		if (getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_SAVE)) {
				preparedStatement.setLong(1, entity.getId());
				preparedStatement.setString(2, entity.getEmail());
				if (preparedStatement.executeUpdate() == 0) {
					System.err.println("Can't save User with id = " + entity.getId());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
	
	@Override
    public void update(User entity) {
		if (getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_UPDATE)) {
				preparedStatement.setString(1, entity.getEmail());
				preparedStatement.setLong(2, entity.getId());
				if (preparedStatement.executeUpdate() == 0) {
					System.err.println("Can't update User with id = " + entity.getId());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
    public void delete(Long id) {
		if (getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_DELETE)) {
				preparedStatement.setLong(1, id);
				if (preparedStatement.executeUpdate() == 0) {
					System.err.println("Cant't delete/find User with id = " + id);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
	
	@Override
    public Optional<User> findByEmail(String email) {
		if (getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_USER_FIND_BY_EMAIL)) {
				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					User foundUser = new User(resultSet.getLong(1), email);
					return Optional.of(foundUser);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return Optional.empty();
    }
	
}
