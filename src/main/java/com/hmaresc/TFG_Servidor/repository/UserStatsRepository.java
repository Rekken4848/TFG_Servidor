package com.hmaresc.TFG_Servidor.repository;

import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.model.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStats, Long> {
    Optional<UserStats> findByUser(User user);
}
