package com.daylicodework.cookshare.service.recipe;

import com.daylicodework.cookshare.dto.RecipeDto;
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
        Recipe createRequest = request.getRecipe();
        recipe.setTitle(createRequest.getTitle());
        recipe.setCuisine(createRequest.getCuisine());
        recipe.setCategory(createRequest.getCategory());
        recipe.setInstructions(createRequest.getInstructions());
        recipe.setDescription(createRequest.getDescription());
        recipe.setPrepTime(createRequest.getPrepTime());
        recipe.setCookTime(createRequest.getCookTime());
        recipe.setIngredients(createRequest.getIngredients());
        recipe.setUser(user);
        return recipe;
    }

    static Recipe updateRecipe(Recipe existingRecipe, RecipeUpdateRequest request){
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setInstructions(request.getInstructions());
        existingRecipe.setCuisine(request.getCuisine());
        existingRecipe.setPrepTime(request.getPrepTime());
        existingRecipe.setCookTime(request.getCookTime());
        existingRecipe.setCategory(request.getCategory());
        existingRecipe.setIngredients(request.getIngredients());
        return existingRecipe;
    }

    List<RecipeDto> getConvertedRecipes(List<Recipe> recipes);

    RecipeDto convertToDto(Recipe recipe);
}
