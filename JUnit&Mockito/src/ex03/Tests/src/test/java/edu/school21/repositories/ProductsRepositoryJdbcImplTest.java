package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new LinkedList<>(Arrays.asList(
		new Product(1L, "Bread", 6.5D), 
		new Product(2L, "Milk", 8.0D),
        new Product(3L, "Pizza", 15.99D),
		new Product(4L, "Lemon", 6.0D),
        new Product(5L, "Plastic bag", 1.0D))
	);
    private static final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3L, "Pizza", 15.99D);
    private static final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "Orange", 6.0D);
	private static final Product EXPECTED_SAVED_PRODUCT = new Product(6L, "Pepsi", 12.30D);
    private ProductsRepository productsRepository;

    @BeforeEach
    public void init() {
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void findAllTest() {
        Assertions.assertEquals(productsRepository.findAll(), EXPECTED_FIND_ALL_PRODUCTS);
		//	Empty dataBase:
		EmbeddedDatabase emptyDataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
				.addScript("schema.sql")
                .build();
		ProductsRepository emptyRepository = new ProductsRepositoryJdbcImpl(emptyDataSource);
		List<Product> emptyList = new LinkedList<>();
		Assertions.assertEquals(emptyRepository.findAll(), emptyList);
    }

    @Test
    void findByIdTest() {
        Optional<Product> product = productsRepository.findById(3L);
        Assertions.assertTrue(product.isPresent());
        product.ifPresent(value -> Assertions.assertEquals(value, EXPECTED_FIND_BY_ID_PRODUCT));
		
		Optional<Product> emptyProduct = productsRepository.findById(9999L);
        Assertions.assertFalse(emptyProduct.isPresent());
    }

    @Test
    void updateTest() {
        productsRepository.update(new Product(4L, "Orange", 50D));
        Optional<Product> product = productsRepository.findById(4L);
        Assertions.assertTrue(product.isPresent());
        product.ifPresent(value -> Assertions.assertEquals(value, EXPECTED_UPDATED_PRODUCT));
		
		productsRepository.update(new Product(999999L, "QWERTY", 999999D));
    }

    @Test
    void saveTest() {
        Product product = new Product(1L, "Pepsi", 12.30D);
        productsRepository.save(product);
        Optional<Product> last = productsRepository.findById(6L);
        Assertions.assertTrue(last.isPresent());
        last.ifPresent(value -> Assertions.assertEquals(value, EXPECTED_SAVED_PRODUCT));
		Assertions.assertEquals(product.getIdentifier(), EXPECTED_SAVED_PRODUCT.getIdentifier());
    }

    @Test
    public void deleteTest() {
        productsRepository.delete(5L);
        Optional<Product> product = productsRepository.findById(5L);
        Assertions.assertFalse(product.isPresent());
		productsRepository.delete(9999L);
		Optional<Product> productNine = productsRepository.findById(9999L);
        Assertions.assertFalse(productNine.isPresent());
    }
}