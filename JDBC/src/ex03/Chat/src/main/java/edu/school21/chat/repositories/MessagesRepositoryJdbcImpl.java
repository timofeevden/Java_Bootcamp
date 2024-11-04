package edu.school21.chat.repositories;

import java.util.Optional;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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

	@Override
    public void save(Message message) throws NotSavedSubEntityException {
		Connection connection = null;
		Statement st = null;
		ResultSet resultSet = null;
        try {
			connection = dataSource.getConnection();
            st = connection.createStatement();

			User user = findUser(message.getAuthor().getId(), st);
			if (user == null) {
                throw new NotSavedSubEntityException("Cant save message! Cant find user with id = " + message.getAuthor().getId());
            }
			Chatroom chatroom = findChatroom(message.getRoom().getId(), st);
			if (chatroom == null) {
                throw new NotSavedSubEntityException("Cant save message! Cant find chatroom with id = " + message.getRoom().getId());
            }
			
			String query = "INSERT INTO messages (author_id, chatroom_id, text, date_time) VALUES (" + 
			message.getAuthor().getId() + ", " + message.getRoom().getId() + ", '" + message.getText() + "', '" + message.getDateTime() + "')"
			+ "RETURNING id;";
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) {
                throw new NotSavedSubEntityException("Cant save message! Id was not return!");
            }
            message.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new NotSavedSubEntityException("Cant save message!" + e.getMessage());
        }
    }

	@Override
    public void update(Message message) {
        try {
            Connection connection = dataSource.getConnection();
			PreparedStatement st = connection.prepareStatement("UPDATE messages SET author_id = ?, chatroom_id = ?, text = ?, date_time = ? WHERE id = ?;");
			st.setObject(1, message.getAuthor() != null ? message.getAuthor().getId() : null);
            st.setObject(2, message.getRoom() != null ? message.getRoom().getId() : null);
            st.setString(3, message.getText());
			st.setTimestamp(4, message.getDateTime() != null ? Timestamp.valueOf(message.getDateTime()) : null);
            st.setLong(5, message.getId());
			
            if (st.executeUpdate() == 0) {
                throw new NotSavedSubEntityException("Cant update message");
            }
        } catch (SQLException e) {
			throw new NotSavedSubEntityException("SQL Exception - " + e.getMessage());
        }
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

