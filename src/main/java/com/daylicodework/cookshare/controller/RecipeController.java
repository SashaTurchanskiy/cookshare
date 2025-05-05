package com.daylicodework.cookshare.controller;

import com.daylicodework.cookshare.dto.RecipeDto;
import com.daylicodework.cookshare.model.Recipe;
import com.daylicodework.cookshare.request.CreateRecipeRequest;
import com.daylicodework.cookshare.request.RecipeUpdateRequest;
import com.daylicodework.cookshare.service.recipe.IRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final IRecipeService recipeService;


    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody CreateRecipeRequest request) {
        Recipe recipe = recipeService.createRecipe(request);
        return ResponseEntity.ok(recipeService.convertToDto(recipe));
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<RecipeDto> recipeDto = recipeService.getConvertedRecipes(recipes);
        return ResponseEntity.ok(recipeDto);
    }
    @GetMapping("/{recipeId}/recipe")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long recipeId) {
        RecipeDto recipeDto = recipeService.convertToDto(recipeService.getRecipeById(recipeId));
        return ResponseEntity.ok(recipeDto);
    }
    @PutMapping("/{recipeId}/update")
    public ResponseEntity<RecipeDto> updateRecipe(@RequestBody RecipeUpdateRequest request,@PathVariable Long recipeId) {
        Recipe updatedRecipe = recipeService.updateRecipe(request, recipeId);
        RecipeDto recipeDto = recipeService.convertToDto(updatedRecipe);
        return ResponseEntity.ok(recipeDto);
    }
    @DeleteMapping("/{recipeId}/delete")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/categories")
    public ResponseEntity<Set<String>> getAllCategories() {
        return ResponseEntity.ok(recipeService.getAllRecipeCategories());
    }
    @GetMapping("/cuisines")
    public ResponseEntity<Set<String>> getAllCuisines() {
        return ResponseEntity.ok(recipeService.getAllRecipeCuisines());
    }

}


