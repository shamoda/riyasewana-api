package com.riyasewana.api.service;

import com.riyasewana.api.exception.RiyasewanaException;
import com.riyasewana.api.model.User;
import com.riyasewana.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User insertUser(User user) {
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(String id) throws RiyasewanaException {
        return repository.findById(id).orElseThrow(
                () -> new RiyasewanaException("No such user found.")
        );
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public User login(String email, String pwd) {
        User tempUser = repository.findById(email).get();
        if (tempUser == null) {
            return null;
        } else if (!tempUser.getPassword().equals(pwd)) {
            return null;
        } else {
            return tempUser;
        }
    }
}
