package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.sql.DataSource;


@Component("messagesRepositoryJdbc")
@Scope("singleton")
public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM messages WHERE id = ?;";
    private static final String QUERY_SAVE = "INSERT INTO messages (author_id, chatroom_id, text, date_time) VALUES (?, ?, ?, ?);";
	private static final String QUERY_UPDATE = "UPDATE messages SET author_id = ?, chatroom_id = ?, text = ?, date_time = ? WHERE id = ?;";
	private static final String QUERY_DELETE = "DELETE FROM messages WHERE id = ?;";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessagesRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }	
	
	@Override
    public Optional<Message> findById(Long id) {
        Message foundMsg = jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, new MessageRowMapper(), id);
        return foundMsg != null ? Optional.of(foundMsg) : Optional.empty();
    }

	@Override
    public void save(Message message) {
        if (jdbcTemplate.update(QUERY_SAVE,
            message.getAuthor() != null ? message.getAuthor().getId() : null,
            message.getRoom() != null ? message.getRoom().getId() : null,
            message.getText(),
            message.getDateTime() != null ? Timestamp.valueOf(message.getDateTime()) : null) == 0) {
			    System.err.println("Can't save Message with id = " + message.getId());
		}
    }

	@Override
    public void update(Message message) {
        if (jdbcTemplate.update(QUERY_UPDATE, message.getAuthor() != null ? message.getAuthor().getId() : null,
            message.getRoom() != null ? message.getRoom().getId() : null,
            message.getText(),
            message.getDateTime() != null ? Timestamp.valueOf(message.getDateTime()) : null,
            message.getId()) == 0) {
            System.err.println("Can't update Message with id = " + message.getId());
        }
    }
	
	@Override
    public void delete(Long id) {
        if (jdbcTemplate.update(QUERY_DELETE, id) == 0) {
            System.err.println("Cant't delete/find Messaage with id = " + id);
        }
    }


    private class MessageRowMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();
        message.setId(rs.getLong("id"));
        message.setText(rs.getString("text"));
        message.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        message.setAuthor(new User(rs.getLong("author_id")));        
        message.setRoom(new Chatroom(rs.getLong("chatroom_id")));
        return message;
    }
}
}

