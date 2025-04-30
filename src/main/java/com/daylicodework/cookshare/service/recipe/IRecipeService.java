package com.daylicodework.cookshare.service.recipe;

import com.daylicodework.cookshare.model.Recipe;
import com.daylicodework.cookshare.model.User;
import com.daylicodework.cookshare.request.CreateRecipeRequest;
import com.daylicodework.cookshare.request.RecipeUpdateRequest;

import java.util.List;
import java.util.Set;

public interface IRecipeService {
    Recipe createRecipe(CreateRecipeRequest request);
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(Long id);
    Recipe updateRecipe(RecipeUpdateRequest request, Long recipeId);

    void deleteRecipe(Long id);

    Set<String> getAllRecipeCategories();
    Set<String> getAllRecipeCuisines();

    static Recipe createRecipe(CreateRecipeRequest request, User user) {
        Recipe recipe = new Recipe();
        Recipe createdRecipe = request.getRecipe();
        recipe.setTitle(createdRecipe.getTitle());
        recipe.setInstructions(createdRecipe.getInstructions());
        recipe.setPrepTime(createdRecipe.getPrepTime());
        recipe.setCookTime(createdRecipe.getCookTime());
        recipe.setCategory(createdRecipe.getCategory());
        recipe.setDescription(createdRecipe.getDescription());
        recipe.setCuisine(createdRecipe.getCuisine());
        recipe.setIngredients(createdRecipe.getIngredients());
        recipe.setUser(user);
        return recipe;
    }

    static Recipe updateRecipe(Recipe existingRecipe, RecipeUpdateRequest request) {
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setInstructions(request.getInstructions());
        existingRecipe.setPrepTime(request.getPrepTime());
        existingRecipe.setCookTime(request.getCookTime());
        existingRecipe.setCategory(request.getCategory());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setCuisine(request.getCuisine());
        existingRecipe.setIngredients(request.getIngredients());
        return existingRecipe;
    }

}
