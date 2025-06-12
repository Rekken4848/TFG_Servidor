package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.UserStats;
import com.hmaresc.TFG_Servidor.model.UsersRecipes;
import com.hmaresc.TFG_Servidor.repository.UserStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserStatsService {

    @Autowired
    private UserStatsRepository userStatsRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<UserStats> getAllUserStats() {
        return userStatsRepository.findAll();
    }

    public Optional<UserStats> getUserStatsById(Long id) {
        return userStatsRepository.findById(id);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public UserStats createUserStats(UserStats userStats) {
        return userStatsRepository.save(userStats);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<UserStats> updateUserStats(Long id, UserStats userStatsDetails) {
        Optional<UserStats> userStatsOptional = userStatsRepository.findById(id);
        if (!userStatsOptional.isPresent()) {
            return Optional.empty();
        }
        userStatsDetails.setId(id);
        UserStats updatedUserStats = userStatsRepository.save(userStatsDetails);
        return Optional.of(updatedUserStats);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteUserStats(Long id) {
        if (!userStatsRepository.existsById(id)) {
            return false;
        }
        userStatsRepository.deleteById(id);
        return true;
    }
}
