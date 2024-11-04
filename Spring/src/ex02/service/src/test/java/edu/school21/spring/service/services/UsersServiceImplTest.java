package edu.school21.spring.service.services;

import edu.school21.spring.service.config.TestApplicationConfig;
import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UsersRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.SQLException;
import java.util.Optional;

public class UsersServiceImplTest {
    private static UsersRepository repository;
    private static UsersService service;

    @BeforeAll
    static void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        repository = context.getBean("usersTestRepository", UsersRepository.class);
        service = new UsersServiceImpl(repository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1@mail.ru", "2@mail.ru", "3@mail.ru", "4@mail.ru", "5@mail.ru"})
    void testSignUp(String email) {
        Assertions.assertNotNull(service.signUp(email));
        Optional<User> user = repository.findByEmail(email);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(email, user.get().getEmail());
    }

    @AfterAll
    static void printFinish() {
        System.out.println("\\\\\\\\\\\\\\\\    T E S T     F I N I S H E D  !");
    }
    
}