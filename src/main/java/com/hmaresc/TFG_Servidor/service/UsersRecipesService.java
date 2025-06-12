package com.hmaresc.TFG_Servidor.service;

import com.hmaresc.TFG_Servidor.model.Recipe;
import com.hmaresc.TFG_Servidor.model.UsersRecipes;
import com.hmaresc.TFG_Servidor.repository.UsersRecipesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersRecipesService {

    @Autowired
    private UsersRecipesRepository usersRecipesRepository;

    // .................................................................
    //  << GET >>
    // .................................................................
    public List<UsersRecipes> getAllUsersRecipes() {
        return usersRecipesRepository.findAll();
    }

    public Optional<UsersRecipes> getUsersRecipesById(Long id) {
        return usersRecipesRepository.findById(id);
    }

    // .................................................................
    //  << POST >>
    // .................................................................
    public UsersRecipes createUsersRecipes(UsersRecipes usersRecipes) {
        return usersRecipesRepository.save(usersRecipes);
    }

    // .................................................................
    //  << UPDATE >>
    // .................................................................
    public Optional<UsersRecipes> updateUsersRecipes(Long id, UsersRecipes usersRecipesDetails) {
        Optional<UsersRecipes> usersRecipesOptional = usersRecipesRepository.findById(id);
        if (!usersRecipesOptional.isPresent()) {
            return Optional.empty();
        }
        usersRecipesDetails.setId(id);
        UsersRecipes updatedUsersRecipes = usersRecipesRepository.save(usersRecipesDetails);
        return Optional.of(updatedUsersRecipes);
    }

    // .................................................................
    //  << DELETE >>
    // .................................................................
    public boolean deleteUsersRecipes(Long id) {
        if (!usersRecipesRepository.existsById(id)) {
            return false;
        }
        usersRecipesRepository.deleteById(id);
        return true;
    }
}
