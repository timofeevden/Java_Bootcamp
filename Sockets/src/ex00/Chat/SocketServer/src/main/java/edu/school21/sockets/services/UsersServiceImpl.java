package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;
import java.util.List;

@Component("userService")
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;
    private final PasswordEncoder pswdEncoder;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbc") UsersRepository repository, @Qualifier("bCryptPasswordEncoder")PasswordEncoder pswdEncoder) {
        this.repository = repository;
        this.pswdEncoder = pswdEncoder;
    }

    @Override
    public boolean signUp(String name) {
        return signUp(name, UUID.randomUUID().toString());
    }

    @Override
    public boolean signUp(String name, String password) {
        if (repository.findByName(name) == null) {
            return false;
        }
        List<User> allUsers = repository.findAll();
        Long id = allUsers.isEmpty() ? 1 : (Long) allUsers.get(allUsers.size() - 1).getId() + 1;
        repository.save(new User(id, name, pswdEncoder.encode(password)));
        return true;
    }
}