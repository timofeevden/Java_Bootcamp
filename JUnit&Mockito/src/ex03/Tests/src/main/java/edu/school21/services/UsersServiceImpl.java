package edu.school21.services;

import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.exceptions.AlreadyAuthenticatedException;

public class UsersServiceImpl {
    private final UsersRepository userRepository;

    public UsersServiceImpl(UsersRepository repository) {
        this.userRepository = repository;
    }

    public boolean authenticate(String login, String password) throws AlreadyAuthenticatedException {
		User user = userRepository.findByLogin(login);
		if (user != null && user.getPassword().equals(password)) {
			if (user.getAuthenticationStatus() == true) {
				throw new AlreadyAuthenticatedException("User already authenticated");
			}
			user.setAuthenticationStatus(true);
			userRepository.update(user);
			return true;
		}
        return false;
    }
}