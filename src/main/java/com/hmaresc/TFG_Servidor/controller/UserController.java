package com.hmaresc.TFG_Servidor.controller;

import com.hmaresc.TFG_Servidor.service.UserService;
import com.hmaresc.TFG_Servidor.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // .......................................................
    // .......................................................
    // .......................GET.............................
    // .......................................................
    // .......................................................
    // .......................................................
    // GET /users/
    // .......................................................
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // .......................................................
    // GET /users/<id>
    // .......................................................
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/created-after/{date}")
    public List<User> getUsersCreatedAfter(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return userService.getUsersCreatedAfter(parsedDate);
    }

    // .......................................................
    // .......................................................
    // .......................POST............................
    // .......................................................
    // .......................................................
    // .......................................................
    // POST /users/
    // .......................................................
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user.setCreationDate(LocalDate.now());
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    // .......................................................
    // .......................................................
    // .....................UPDATE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // UPDATE /users/<id>
    // .......................................................
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.updateUser(id, userDetails);
        return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // .......................................................
    // .......................................................
    // .....................DELETE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // DELETE /users/<id>
    // .......................................................
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}