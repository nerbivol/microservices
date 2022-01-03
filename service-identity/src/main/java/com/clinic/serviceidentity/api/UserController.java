package com.clinic.serviceidentity.api;

import com.clinic.serviceidentity.api.dto.UserDto;
import com.clinic.serviceidentity.repo.model.User;
import com.clinic.serviceidentity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userService.fetchAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable long id) {
        try {
            final User user = userService.fetchById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody UserDto user) {
        final String email = user.getEmail();
        final String password = user.getPassword();
        final String firstname = user.getFirstname();
        final String lastname = user.getLastname();
        final String phone = user.getPhone();
        try {
            final long id = userService.create(email, password, firstname, lastname, phone);
            final String location = String.format("/users/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UserDto user) {
        final String email = user.getEmail();
        final String password = user.getPassword();
        final String firstname = user.getFirstname();
        final String lastname = user.getLastname();
        final String phone = user.getPhone();
        try {
            userService.update(id, email, password, firstname, lastname, phone);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
