package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.backend.core.service.UserService;
import com.epam.rd.backend.web.utill.CaseInsensitiveUserRoleEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserRole.class, new CaseInsensitiveUserRoleEditor());
    }

    @RequestMapping(value = "/users",
            method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUser() {
        final List<User> users = service.getAllUsers();
        ResponseEntity<List<User>> responseEntity;
        if (users.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(users, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/users/{role}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable("role") final UserRole role) {
        ResponseEntity<List<User>> responseEntity;
        final List<User> users = service.getUsersByRoles(Collections.singleton(role));
        if (users.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(users, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/user/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") final int id) {
        final User user = service.getUserById(id);
        ResponseEntity<User> responseEntity;
        if (user != null) {
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/user/",
            method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@RequestBody final User user, UriComponentsBuilder builder) {
        ResponseEntity<Void> responseEntity;
        if (isUserExistByEmail(user)) {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            final User newUser = service.addUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/user/{id}").buildAndExpand(newUser.getId()).toUri());
            responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    private boolean isUserExistByEmail(final User user) {
        return service.getUserByEmail(user.getEmail()) != null;
    }

    @RequestMapping(value = "/user/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<User> updateUserById(@PathVariable("id") final int id, @RequestBody final User user) {
        ResponseEntity<User> responseEntity;
        final User currentUser = service.getUserById(id);
        if (currentUser == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword(user.getPassword());
            final User updateUser = service.updateUser(currentUser);
            responseEntity = new ResponseEntity<>(updateUser, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/user/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") final int id) {
        ResponseEntity<Void> responseEntity;
        final User currentUser = service.getUserById(id);
        if (currentUser == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.deleteUserById(id);
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/users",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllUsers() {
        ResponseEntity<Void> responseEntity;
        final long usersCount = service.getUsersCount();
        if (usersCount == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            service.deleteAllUsers();
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }
}
