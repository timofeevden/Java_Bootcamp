package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDateTime;
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

			Connection connection = dataSource.getConnection();
			Statement st = connection.createStatement();
			String query = "SELECT MAX(id) FROM users";
			ResultSet resultSet = st.executeQuery(query);
			if (!resultSet.next()) {
				throw new Exception("Users table is empty!");
			}
			Long lastId = resultSet.getLong(1);
			User author = new User(lastId, "user", "user", new ArrayList(), new ArrayList());

			query = "SELECT MAX(id) FROM chatrooms";
			resultSet = st.executeQuery(query);
			if (!resultSet.next()) {
				throw new Exception("Chatrooms table is empty!");
			}
			Chatroom room = new Chatroom(lastId, "room", author, new ArrayList());
			Message message = new Message(null, author, room, "Hello! TEST SAVE MESSAGE", LocalDateTime.now());
			MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
			messagesRepository.save(message);
			System.out.println(message.getId());

			User author2 = new User(99999L, "user", "user", new ArrayList(), new ArrayList());
			Chatroom room2 = new Chatroom(99999L, "room", author, new ArrayList());
			try {
				Message message2 = new Message(null, author, room2, "Hello! TEST SAVE MESSAGE", LocalDateTime.now());
				messagesRepository.save(message2);
			} catch (NotSavedSubEntityException e) {
				System.out.println("Catched with uncorrect chatroom id - " + e.getMessage());
			}

			try {
				Message message3 = new Message(null, author2, room, "Hello! TEST SAVE MESSAGE", LocalDateTime.now());
				messagesRepository.save(message3);
			} catch (NotSavedSubEntityException e) {
				System.out.println("Catched with uncorrect user id - " + e.getMessage());
			}
        } catch (Exception e) {
            System.out.println("Exception! " + e.getMessage());
        }
    }
}
