package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.model.UserStats;
import com.hmaresc.TFG_Servidor.repository.UserStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStatsServiceTest {

    @Mock
    private UserStatsRepository userStatsRepository;

    @InjectMocks
    private UserStatsService userStatsService;

    private UserStats userStats;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        userStats = new UserStats();
        userStats.setId(1L);
        userStats.setUser(user);
        userStats.setAge(25);
        userStats.setGender("Male");
        userStats.setHeight(180f);
        userStats.setWeight(75f);
        userStats.setActivityLevel("Medium");
        userStats.setFatPercentage(15.5f);
    }

    @Test
    void getAllUserStats_shouldReturnList() {
        when(userStatsRepository.findAll()).thenReturn(List.of(userStats));

        List<UserStats> result = userStatsService.getAllUserStats();

        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getUser().getId());
    }

    @Test
    void getUserStatsById_shouldReturnUserStats() {
        when(userStatsRepository.findById(1L)).thenReturn(Optional.of(userStats));

        Optional<UserStats> result = userStatsService.getUserStatsById(1L);

        assertTrue(result.isPresent());
        assertEquals(25, result.get().getAge());
    }

    @Test
    void getUserStatsByUser_shouldReturnUserStats() {
        when(userStatsRepository.findByUser(user)).thenReturn(Optional.of(userStats));

        Optional<UserStats> result = userStatsService.getUserStatsByUser(user);

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUser().getUsername());
    }

    @Test
    void createUserStats_shouldSaveAndReturnUserStats() {
        when(userStatsRepository.save(userStats)).thenReturn(userStats);

        UserStats result = userStatsService.createUserStats(userStats);

        assertNotNull(result);
        assertEquals(75f, result.getWeight());
    }

    @Test
    void updateUserStats_shouldReturnUpdatedStatsIfExists() {
        UserStats updated = new UserStats();
        updated.setUser(user);
        updated.setAge(30);
        updated.setGender("Male");
        updated.setHeight(182f);
        updated.setWeight(78f);
        updated.setActivityLevel("High");
        updated.setFatPercentage(13.0f);

        when(userStatsRepository.findById(1L)).thenReturn(Optional.of(userStats));
        when(userStatsRepository.save(any(UserStats.class))).thenAnswer(i -> i.getArgument(0));

        Optional<UserStats> result = userStatsService.updateUserStats(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(30, result.get().getAge());
        assertEquals(1L, updated.getId());
    }

    @Test
    void updateUserStats_shouldReturnEmptyIfNotExists() {
        when(userStatsRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<UserStats> result = userStatsService.updateUserStats(999L, userStats);

        assertTrue(result.isEmpty());
        verify(userStatsRepository, never()).save(any());
    }

    @Test
    void deleteUserStats_shouldReturnTrueIfExists() {
        when(userStatsRepository.existsById(1L)).thenReturn(true);

        boolean result = userStatsService.deleteUserStats(1L);

        assertTrue(result);
        verify(userStatsRepository).deleteById(1L);
    }

    @Test
    void deleteUserStats_shouldReturnFalseIfNotExists() {
        when(userStatsRepository.existsById(999L)).thenReturn(false);

        boolean result = userStatsService.deleteUserStats(999L);

        assertFalse(result);
        verify(userStatsRepository, never()).deleteById(any());
    }
}