package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.repository.UserRepository;
import com.hmaresc.TFG_Servidor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getUsersCreatedAfter(LocalDate date) {
        return userRepository.findAllByCreationDateAfter(date);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<User> updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }
        userDetails.setId(id);
        User updatedUser = userRepository.save(userDetails);
        return Optional.of(updatedUser);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
