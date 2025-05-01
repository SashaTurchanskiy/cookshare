package com.daylicodework.cookshare.controller;

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
    public ResponseEntity<Recipe> createRecipe(@RequestBody CreateRecipeRequest request) {
        return ResponseEntity.status(200).body(recipeService.createRecipe(request));
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
    @GetMapping("/{recipeId}/recipe")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long recipeId) {
        return ResponseEntity.ok(recipeService.getRecipeById(recipeId));
    }
    @PutMapping("/{recipeId}/update")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody RecipeUpdateRequest request,@PathVariable Long recipeId) {
        return ResponseEntity.status(200).body(recipeService.updateRecipe(request, recipeId));
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


