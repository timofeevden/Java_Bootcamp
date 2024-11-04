package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    private EmbeddedDatabase db;

    @BeforeEach
    public void init() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    @Test
    public void connectionTest() {
        try {
            Assertions.assertNotNull(db.getConnection());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
    @AfterEach
    public void tearDown() {
        db.shutdown();
    }

}