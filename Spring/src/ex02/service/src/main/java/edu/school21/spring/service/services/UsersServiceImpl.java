package edu.school21.spring.service.services;

import edu.school21.spring.service.models.User;
import edu.school21.spring.service.repositories.UsersRepository;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;
import java.util.List;

@Component("userService")
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public String signUp(String email) {
        List<User> allUsers = repository.findAll();
        Long id = allUsers.isEmpty() ? 1 : (Long) allUsers.get(allUsers.size() - 1).getId() + 1;
        String password = UUID.randomUUID().toString();
        repository.save(new User(id, email, password));
        return password;
    }
}