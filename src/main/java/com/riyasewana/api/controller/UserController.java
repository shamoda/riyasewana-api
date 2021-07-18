package com.riyasewana.api.controller;

import com.riyasewana.api.exception.RiyasewanaException;
import com.riyasewana.api.model.User;
import com.riyasewana.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/user")
    public ResponseEntity<?> insertUser(@RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("name") String name,
                                        @RequestParam("role") String role,
                                        @RequestParam("contact") String contact,
                                        @RequestParam("location") String location)
    {
        User temUser = new User(email, password, name, role, contact, location);
        return new ResponseEntity<>(service.insertUser(temUser), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
        } catch (RiyasewanaException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        service.deleteById(id);
        return new ResponseEntity<>("user recode deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestParam("email") String email,
                                   @RequestParam("password") String password)
    {
        return new ResponseEntity<>(service.login(email, password), HttpStatus.OK);
    }
}
