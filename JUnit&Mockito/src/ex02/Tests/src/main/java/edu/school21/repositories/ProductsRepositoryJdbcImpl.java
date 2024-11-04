package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.LinkedList;
import java.util.List;


public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM products WHERE identifier = ?;";
    public static final String QUERY_UPDATE_PRODUCT = "UPDATE products SET name = ?, price = ? WHERE identifier = ?;";
    public static final String QUERY_SAVE_PRODUCT = "INSERT INTO products (name, price) VALUES (?, ?);";
    public static final String QUERY_DELETE_PRODUCT = "DELETE FROM products WHERE identifier = ?;";
    public static final String QUERY_FIND_ALL = "SELECT * FROM products;";
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(QUERY_FIND_ALL)) {
			while (resultSet.next()) {
				products.add(new Product (resultSet.getLong("identifier"), resultSet.getString("name"), resultSet.getDouble("price")));
			}
        } catch (SQLException e) {
		}
		return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_BY_ID)) {

			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(new Product (resultSet.getLong("identifier"), resultSet.getString("name"), resultSet.getDouble("price")));
				}
			}
        } catch (SQLException e) {
		}
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getIdentifier());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
		}
    }

    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SAVE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
			preparedStatement.executeUpdate();
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					product.setIdentifier(generatedKeys.getLong(1));
				}
			}
        } catch (SQLException e) {
		}
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE_PRODUCT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
		}
    }
}