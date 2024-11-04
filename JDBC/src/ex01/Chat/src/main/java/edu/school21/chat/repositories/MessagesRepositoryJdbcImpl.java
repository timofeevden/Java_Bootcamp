package edu.school21.chat.repositories;

import java.util.Optional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }	
	
	@Override
    public Optional<Message> findById(Long id) {
        try {
			String query = "SELECT * FROM messages WHERE id = " + id + ";";
			Connection connection = dataSource.getConnection();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()) {
				Long messageId = resultSet.getLong(1);
				Long userId = resultSet.getLong(2);
				Long chatroomId = resultSet.getLong(3);
				String messageText = resultSet.getString(4);
				LocalDateTime messageTime = resultSet.getTimestamp(5) != null ? resultSet.getTimestamp(5).toLocalDateTime() : null;
				User user =  findUser(userId, st);
				Chatroom chatroom = findChatroom(chatroomId, st);
				
				return Optional.of(new Message(messageId, user, chatroom, messageText, messageTime));
			}
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
		
        return Optional.empty();
    }
	
	private User findUser(Long id, Statement st) throws SQLException {
        String query = "SELECT * FROM users WHERE id = " + id + ";";
		ResultSet resultSet = st.executeQuery(query);
		if (resultSet.next()) {
			return new User(id, resultSet.getString(2), resultSet.getString(3));
		}
		return null; 
    }
	
    private Chatroom findChatroom(Long id, Statement st) throws SQLException {
        String query = "SELECT * FROM chatrooms WHERE id = " + id + ";";
		ResultSet resultSet = st.executeQuery(query);
		if (resultSet.next()) {
			return new Chatroom(id, resultSet.getString(2));
		}
		return null;
    }	
}

