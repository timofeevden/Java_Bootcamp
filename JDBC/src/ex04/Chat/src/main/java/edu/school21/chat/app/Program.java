package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.List;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Program {
    public static void main(String[] args) {
        try {			
			HikariConfig config = new HikariConfig("src/main/resources/db.properties");			
			DataSource dataSource = new HikariDataSource(config);
			UsersRepository userRepository = new UsersRepositoryJdbcImpl(dataSource);

			List<User> allUsers = userRepository.findAll(0, 5);
			System.out.println("\nNow lets look 5 users\n");
			for(User user : allUsers) {
				System.out.println(user);
			}

			System.out.println("\nNow lets look users 4, 5 (Second page in 3 size)\n");

			allUsers = userRepository.findAll(1, 3);
			for(User user : allUsers) {
				System.out.println(user);
				System.out.println("=== Created rooms:");
				if (!user.getCreatedRooms().isEmpty()) {
					for (Chatroom room : user.getCreatedRooms()) {
						System.out.println(room);
					}
				}
				System.out.println("=== Using rooms:");
				if (!user.getRooms().isEmpty()) {
					for (Chatroom room : user.getRooms()) {
						System.out.println(room);
					}
				}
			}
        } catch (Exception e) {
            System.out.println("Exception! " + e.getMessage());
        }
    }
}
