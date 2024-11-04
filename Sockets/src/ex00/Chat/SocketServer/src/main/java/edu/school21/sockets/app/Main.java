package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String portFlag = "--server-port=";
        if (!args[0].startsWith(portFlag)) {
            System.err.println("You need to write '--server-port=' in first argument!");
            System.exit(-1);
        }
        try {
            createTable();
            int port = Integer.parseInt(args[0].substring(portFlag.length()));
            ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
            Server server = context.getBean(Server.class);
            server.init(port);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     private static void createTable() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:postgresql://localhost:5432/Day09");
        config.setUsername("postgres");
        config.setPassword("1");
		DataSource dataSource = new HikariDataSource(config);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(id BIGSERIAL PRIMARY KEY, name VARCHAR(255), password VARCHAR(255));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}