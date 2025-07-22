package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Food;
import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }

    public Optional<Food> findByCode(String code) {
        return foodRepository.findByCode(code);
    }

    public Optional<List<Food>> getFoodByUser(User user) {
        return foodRepository.findByUser(user);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<Food> updateFood(Long id, Food foodDetails) {
        Optional<Food> foodOptional = foodRepository.findById(id);
        if (!foodOptional.isPresent()) {
            return Optional.empty();
        }
        foodDetails.setId(id);
        Food updatedFood = foodRepository.save(foodDetails);
        return Optional.of(updatedFood);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            return false;
        }
        foodRepository.deleteById(id);
        return true;
    }
}
