package com.clinic.serviceidentity.service;

import com.clinic.serviceidentity.repo.UserRepo;
import com.clinic.serviceidentity.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserService {
    private final UserRepo userRepo;

    public List<User> fetchAll() {
        return userRepo.findAll();
    }

    public User fetchById(long id) throws IllegalArgumentException {
        final Optional<User> byId = userRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("User with id = %d was not found", id));
        return byId.get();
    }

    public long create(String email, String password, String firstname, String lastname, String phone) {
        final Optional<User> byName = Optional.ofNullable(userRepo.findByEmail(email));
        if (byName.isPresent()) throw new IllegalArgumentException(String.format("User with username = %s already exists", email));
        final User user = new User(email, password, firstname, lastname, phone);
        final User save = userRepo.save(user);
        return save.getId();
    }

    public void update(long id, String email, String password, String firstname, String lastname, String phone)
            throws IllegalArgumentException {
        final Optional<User> byId = userRepo.findById(id);
        if (byId.isEmpty()) throw new IllegalArgumentException(String.format("User with id = %d not found", id));
        final Optional<User> byName = Optional.ofNullable(userRepo.findByEmail(email));
        if (byName.isPresent()) throw new IllegalArgumentException(String.format("User with email = %s already exists", email));
        final User user = byId.get();
        if(email != null && !email.isEmpty()) user.setEmail(email);
        if(password != null && !password.isEmpty()) user.setPassword(password);
        if(firstname != null && !firstname.isEmpty()) user.setFirstname(firstname);
        if(lastname != null && !lastname.isEmpty()) user.setLastname(lastname);
        if(phone != null && !phone.isEmpty()) user.setPhone(phone);
        userRepo.save(user);
    }

    public void delete(long id) {
        userRepo.deleteById(id);
    }
}
