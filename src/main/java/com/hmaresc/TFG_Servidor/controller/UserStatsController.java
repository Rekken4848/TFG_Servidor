package com.hmaresc.TFG_Servidor.controller;

import com.hmaresc.TFG_Servidor.dto.UserWithStatsDTO;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.model.UserStats;
import com.hmaresc.TFG_Servidor.service.UserService;
import com.hmaresc.TFG_Servidor.service.UserStatsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userstats")
public class UserStatsController {

    @Autowired
    private UserStatsService userStatsService;
    @Autowired
    private UserService userService;

    // .......................................................
    // .......................................................
    // .......................GET.............................
    // .......................................................
    // .......................................................
    // .......................................................
    // GET /userstats/
    // .......................................................
    @GetMapping
    public List<UserStats> getAllUserStats() {
        return userStatsService.getAllUserStats();
    }

    // .......................................................
    // GET /userstats/<id>
    // .......................................................
    @GetMapping("/{id}")
    public ResponseEntity<UserStats> getUserStatsById(@PathVariable Long id) {
        Optional<UserStats> userStats = userStatsService.getUserStatsById(id);

        if (userStats.isPresent()) {
            return ResponseEntity.ok(userStats.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // GET /userstats/user/<email>
    // .......................................................
    @GetMapping("/user/{email}")
    public ResponseEntity<UserWithStatsDTO> getUserAndStatsByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        System.out.println("Email: " + email);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("User: " + user.get().getId() + " " + user.get().getLastName());

        Optional<UserStats> userStats = userStatsService.getUserStatsByUser(user.get());
        if (!userStats.isPresent()) {
            System.out.println("UserStats no esta ");
            return ResponseEntity.notFound().build();
        }

        System.out.println("UserStats: " + userStats.get().toString());

        UserWithStatsDTO dto = new UserWithStatsDTO();
        dto.setId(user.get().getId());
        dto.setEmail(user.get().getEmail());
        dto.setUsername(user.get().getUsername());
        dto.setName(user.get().getName());
        dto.setLastName(user.get().getLastName());
        dto.setUserStats(userStats.get());

        System.out.println("UserWithStatsDTO: " + dto);

        return ResponseEntity.ok(dto);
    }

    // .......................................................
    // .......................................................
    // .......................POST............................
    // .......................................................
    // .......................................................
    // .......................................................
    // POST /userstats/<user_id>
    // .......................................................
    @PostMapping("/{user_id}")
    public ResponseEntity<UserStats> createUserStats(@PathVariable Long user_id, @Valid @RequestBody UserStats userStats) {
        User userWithId = new User();
        userWithId.setId(user_id);
        userStats.setUser(userWithId);
        UserStats created = userStatsService.createUserStats(userStats);
        return ResponseEntity.ok(created);
    }

    // .......................................................
    // .......................................................
    // .....................UPDATE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // UPDATE /userstats/<id>/<user_id>
    // .......................................................
    @PutMapping("/{id}/{user_id}")
    public ResponseEntity<UserStats> updateUserStats(@PathVariable Long id, @PathVariable Long user_id, @Valid @RequestBody UserStats userStatsDetails) {
        User userWithId = new User();
        userWithId.setId(user_id);
        userStatsDetails.setUser(userWithId);
        Optional<UserStats> updatedUserStats = userStatsService.updateUserStats(id, userStatsDetails);
        return updatedUserStats.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // .......................................................
    // UPDATE /userstats/upload-avatar/<user_id>
    // .......................................................
    @PutMapping(value = "/upload-avatar/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(@PathVariable Long user_id, @RequestPart("image") MultipartFile file, @RequestPart("userStats") UserStats userStats) {
        User userWithId = new User();
        userWithId.setId(user_id);
        userStats.setUser(userWithId);
        try {
            userStats.setProfileImage(file.getBytes());
            userStatsService.updateUserStats(userStats.getId(), userStats);
            //return ResponseEntity.ok().build();
            return ResponseEntity.ok(userStats);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar imagen.");
        }
    }

    // .......................................................
    // UPDATE /userstats/user/
    // .......................................................
    @PutMapping("/user")
    public ResponseEntity<?> updateUserAndStats(@Valid @RequestBody UserWithStatsDTO updatedDto) {
        Optional<User> optionalUser = userService.getUserById(updatedDto.getId());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        user.setName(updatedDto.getName());
        user.setLastName(updatedDto.getLastName());
        user.setEmail(updatedDto.getEmail());
        user.setUsername(updatedDto.getUsername());

        userService.updateUser(user.getId(), user);

        /*Optional<UserStats> optionalStats = userStatsService.getUserStatsByUser(user);
        if (!optionalStats.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UserStats stats = optionalStats.get();
        stats.setAge(updatedDto.getUserStats().getAge());
        stats.setGender(updatedDto.getUserStats().getGender());
        stats.setHeight(updatedDto.getUserStats().getHeight());
        stats.setWeight(updatedDto.getUserStats().getWeight());
        stats.setActivityLevel(updatedDto.getUserStats().getActivityLevel());*/

        userStatsService.updateUserStats(updatedDto.getUserStats().getId(), updatedDto.getUserStats());

        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(updatedDto);
    }

    // .......................................................
    // .......................................................
    // .....................DELETE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // DELETE /userstats/<id>
    // .......................................................
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserStats(@PathVariable Long id) {
        boolean deleted = userStatsService.deleteUserStats(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
