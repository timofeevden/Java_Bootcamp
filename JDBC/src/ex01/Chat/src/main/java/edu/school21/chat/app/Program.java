package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.lang.NumberFormatException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Program {
    public static void main(String[] args) {
        try {			
			HikariConfig config = new HikariConfig("src/main/resources/db.properties");			
			DataSource dataSource = new HikariDataSource(config);
			MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource);

			Scanner scanner = new Scanner(System.in);
			System.out.printf("\nEnter a message ID:\n-> ");
			String str = scanner.nextLine();
			Long id = 0L;
			try {
				id = Long.parseLong(str);
			} catch (NumberFormatException e) {
				System.out.println("\nYou need to write number of ID!");
				System.exit(-1);;
			}
			Optional<Message> message = repository.findById(id);

			if (message.isPresent()) {
				System.out.print(message.orElse(null));
			} else {
				System.out.println("Cant find message with id = " + id);
			}
        } catch (Exception e) {
            System.out.println("Exception! " + e.getMessage());
        }
    }
}
