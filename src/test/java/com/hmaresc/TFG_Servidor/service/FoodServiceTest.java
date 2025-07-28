package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Food;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.repository.FoodRepository;
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
class FoodServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodService foodService;

    private Food food;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        food = new Food();
        food.setId(1L);
        food.setUser(user);
        food.setCode("123ABC");
        food.setName("Test Food");
        food.setBrand("BrandX");
    }

    @Test
    void getAllFoods_shouldReturnListOfFoods() {
        List<Food> foods = List.of(food);
        when(foodRepository.findAll()).thenReturn(foods);

        List<Food> result = foodService.getAllFoods();

        assertEquals(1, result.size());
        assertEquals("Test Food", result.get(0).getName());
    }

    @Test
    void getFoodById_shouldReturnFood_whenExists() {
        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));

        Optional<Food> result = foodService.getFoodById(1L);

        assertTrue(result.isPresent());
        assertEquals("123ABC", result.get().getCode());
    }

    @Test
    void getFoodById_shouldReturnEmpty_whenNotFound() {
        when(foodRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Food> result = foodService.getFoodById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByCode_shouldReturnFood_whenExists() {
        when(foodRepository.findByCode("123ABC")).thenReturn(Optional.of(food));

        Optional<Food> result = foodService.findByCode("123ABC");

        assertTrue(result.isPresent());
        assertEquals("Test Food", result.get().getName());
    }

    @Test
    void getFoodByUser_shouldReturnFoods_whenExists() {
        List<Food> foods = List.of(food);
        when(foodRepository.findByUser(user)).thenReturn(Optional.of(foods));

        Optional<List<Food>> result = foodService.getFoodByUser(user);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    void createFood_shouldSaveFood() {
        when(foodRepository.save(food)).thenReturn(food);

        Food result = foodService.createFood(food);

        assertNotNull(result);
        assertEquals("Test Food", result.getName());
    }

    @Test
    void updateFood_shouldUpdate_whenExists() {
        Food updated = new Food();
        updated.setName("Updated Name");
        updated.setUser(user);

        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));
        when(foodRepository.save(any(Food.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Food> result = foodService.updateFood(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void updateFood_shouldReturnEmpty_whenNotFound() {
        when(foodRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Food> result = foodService.updateFood(99L, food);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteFood_shouldDelete_whenExists() {
        when(foodRepository.existsById(1L)).thenReturn(true);

        boolean result = foodService.deleteFood(1L);

        assertTrue(result);
        verify(foodRepository).deleteById(1L);
    }

    @Test
    void deleteFood_shouldReturnFalse_whenNotFound() {
        when(foodRepository.existsById(99L)).thenReturn(false);

        boolean result = foodService.deleteFood(99L);

        assertFalse(result);
    }
}