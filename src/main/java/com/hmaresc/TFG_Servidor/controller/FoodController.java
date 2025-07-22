package com.hmaresc.TFG_Servidor.controller;

import com.hmaresc.TFG_Servidor.model.Food;
import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.User;
import com.hmaresc.TFG_Servidor.service.FoodService;
import com.hmaresc.TFG_Servidor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    // .......................................................
    // .......................................................
    // .......................GET.............................
    // .......................................................
    // .......................................................
    // .......................................................
    // GET /food/
    // .......................................................
    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    // .......................................................
    // GET /food/<id>
    // .......................................................
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Optional<Food> food = foodService.getFoodById(id);

        if (food.isPresent()) {
            return ResponseEntity.ok(food.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // GET /food/code/<code>
    // .......................................................
    @GetMapping("/code/{code}")
    public ResponseEntity<Food> getFoodByCode(@PathVariable String code) {
        Optional<Food> food = foodService.findByCode(code);

        if (food.isPresent()) {
            return ResponseEntity.ok(food.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // GET /food/myfoods/<email>
    // .......................................................
    @GetMapping("/myfoods/{email}")
    public ResponseEntity<List<Food>> getFoodByEmail(@PathVariable String email) {
        Optional<User> userWithId = userService.getUserByEmail(email);
        Optional<List<Food>> foods = foodService.getFoodByUser(userWithId.get());

        if (foods.isPresent()) {
            return ResponseEntity.ok(foods.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // .......................................................
    // .......................................................
    // .......................POST............................
    // .......................................................
    // .......................................................
    // .......................................................
    // POST /food/<email>
    // .......................................................
    @PostMapping("/{email}")
    public ResponseEntity<Food> createFood(@PathVariable String email, @Valid @RequestBody Food food) {
        Optional<User> userWithId = userService.getUserByEmail(email);
        food.setUser(userWithId.get());
        Food created = foodService.createFood(food);
        return ResponseEntity.ok(created);
    }

    // .......................................................
    // .......................................................
    // .....................UPDATE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // UPDATE /food/<id>/<email>
    // .......................................................
    @PutMapping("/{id}/{email}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @PathVariable String email, @Valid @RequestBody Food foodDetails) {
        Optional<User> userWithId = userService.getUserByEmail(email);
        foodDetails.setUser(userWithId.get());
        Optional<Food> updatedFood = foodService.updateFood(id, foodDetails);
        return updatedFood.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // .......................................................
    // .......................................................
    // .....................DELETE............................
    // .......................................................
    // .......................................................
    // .......................................................
    // DELETE /food/<id>
    // .......................................................
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        boolean deleted = foodService.deleteFood(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
