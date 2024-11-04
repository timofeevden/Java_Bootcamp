package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.util.Optional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Program {
    public static void main(String[] args) {
        try {			
			HikariConfig config = new HikariConfig("src/main/resources/db.properties");			
			DataSource dataSource = new HikariDataSource(config);
			MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);

			Connection connection = dataSource.getConnection();
			Statement st = connection.createStatement();
			String query = "SELECT MAX(id) FROM messages";
			ResultSet resultSet = st.executeQuery(query);
			if (!resultSet.next()) {
				throw new Exception("Messages table is empty!");
			}
			Long lastMessageId = resultSet.getLong(1);
			Optional<Message> messageOptional = messagesRepository.findById(lastMessageId);
			if (messageOptional.isPresent()) {
			  Message message = messageOptional.get();
			  System.out.print("\nMESSAGE BEFORE UPDATE:\n" + message);
			  message.setText("Bye");
			  message.setDateTime(null);
			  messagesRepository.update(message);
			  System.out.print("\n\nMESSAGE AFTER UPDATE:\n" + message);
			}
        } catch (Exception e) {
            System.out.println("Exception! " + e.getMessage());
        }
    }
}
