package edu.school21.services;

import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.exceptions.AlreadyAuthenticatedException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.sql.SQLException;

class UsersServiceImplTest {
    @Mock
    private UsersRepository userRepository;
    private UsersServiceImpl usersService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        usersService = new UsersServiceImpl(userRepository);
    }

    @Test
    void correctLoginAndPasswordTest() {
        User user = new User(1L, "Ivan", "123", false);
        Mockito.when(userRepository.findByLogin("Ivan")).thenReturn(user);
		Mockito.doNothing().when(userRepository).update(user);

        Assertions.assertTrue(usersService.authenticate("Ivan", "123"));
        Assertions.assertTrue(user.getAuthenticationStatus());
        Mockito.verify(userRepository).update(user);
    }
	
	@Test
    void alreadyAuthenticatedTest() {
        User user = new User(1L, "Ivan", "123", true);
        Mockito.when(userRepository.findByLogin("Ivan")).thenReturn(user);

		Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> usersService.authenticate("Ivan", "123"));
        Assertions.assertTrue(user.getAuthenticationStatus());
        Mockito.verify(userRepository, Mockito.never()).update(Mockito.any());
    }

    @Test
    void incorrectLoginTest() {
        Mockito.when(userRepository.findByLogin("bad_login")).thenReturn(null);
        Assertions.assertFalse(usersService.authenticate("bad_login", "123"));
        Mockito.verify(userRepository, Mockito.never()).update(Mockito.any());
    }

    @Test
    void incorrectPasswordTest() {
        User user = new User(1L, "Ivan", "123", false);
        Mockito.when(userRepository.findByLogin("Ivan")).thenReturn(user);
        Assertions.assertFalse(usersService.authenticate("Ivan", "bad_password"));
        Mockito.verify(userRepository, Mockito.never()).update(Mockito.any());
    }
}