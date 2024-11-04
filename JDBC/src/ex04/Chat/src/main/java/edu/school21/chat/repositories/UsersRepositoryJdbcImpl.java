package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> allUsers = new LinkedList<>();
        try {
            String query = "SELECT u.id, u.login, u.password, " + 
                            "array_agg(DISTINCT ccr.id) AS created_rooms_id, " +
                            "array_agg(DISTINCT ccr.name) AS created_rooms_names, " +
                            "array_agg(DISTINCT ucr.id) AS used_rooms_id, " +
                            "array_agg(DISTINCT ucr.name) AS used_rooms_names " +
                            "FROM users u " +
                            "LEFT JOIN chatrooms ccr ON ccr.owner_id = u.id " +
                            "LEFT JOIN messages msgs ON msgs.author_id = u.id " +
                            "LEFT JOIN chatrooms ucr ON ucr.id = msgs.chatroom_id " +
                            "GROUP BY u.id " +
                            "ORDER BY u.id " +
                            "LIMIT ? OFFSET ?;";
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, size);
            statement.setInt(2, page * size);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                List<Chatroom> createdRooms = getChatrooms(resultSet, "created_rooms_id", "created_rooms_names");
                List<Chatroom> usedRooms = getChatrooms(resultSet, "used_rooms_id", "used_rooms_names");
                User user = new User(id, login, password, createdRooms, usedRooms);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return allUsers;
    }

    private List<Chatroom> getChatrooms(ResultSet resultSet, String columnId, String columnName) throws SQLException {
        Array arrId = resultSet.getArray(columnId);
        Array arrNames = resultSet.getArray(columnName);
        List<Chatroom> chatrooms = new LinkedList();

        if (arrId != null && arrNames != null) {
            Long[] roomsId = ArrayToLong(arrId);
            String[] roomsName = (String[]) arrNames.getArray();

            for (int i = 0; i < roomsName.length; ++i) {
                if (roomsId[i] != null && roomsName[i] != null) {
                    Chatroom room = new Chatroom(roomsId[i], roomsName[i], null);
                    chatrooms.add(room);
                }
            }
        }

        return chatrooms;
    }

    private Long[] ArrayToLong(Array arr) throws SQLException {
        Object[] idObjectArray = (Object[]) arr.getArray();
        Long[] arrLong = new Long[idObjectArray.length];
        for (int i = 0; i < idObjectArray.length; ++i) {
            if (idObjectArray[i] instanceof Integer) {
                arrLong[i] = ((Integer) idObjectArray[i]).longValue();
            } else {
                arrLong[i] = (Long) idObjectArray[i];
            }
        }

        return arrLong;
    }

}
