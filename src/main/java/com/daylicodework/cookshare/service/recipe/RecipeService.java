package com.daylicodework.cookshare.service.recipe;

import com.daylicodework.cookshare.dto.ImageDto;
import com.daylicodework.cookshare.dto.RecipeDto;
import com.daylicodework.cookshare.dto.UserDto;
import com.daylicodework.cookshare.exception.RecipeNotFoundException;
import com.daylicodework.cookshare.model.Image;
import com.daylicodework.cookshare.model.Recipe;
import com.daylicodework.cookshare.model.User;
import com.daylicodework.cookshare.repository.ImageRepository;
import com.daylicodework.cookshare.repository.RecipeRepository;
import com.daylicodework.cookshare.repository.UserRepository;
import com.daylicodework.cookshare.request.CreateRecipeRequest;
import com.daylicodework.cookshare.request.RecipeUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService implements IRecipeService{

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;


    @Override
    public Recipe createRecipe(CreateRecipeRequest request) {
        if (request == null || request.getUser() == null) {
            throw new IllegalArgumentException("Invalid request");
        }
        User user = Optional.ofNullable(userRepository.findByUsername(request.getUser().getUsername()))
                .map(existingUser-> {
                    existingUser.getRecipe().add(request.getRecipe());
                    return existingUser;
                }).orElseGet(()->{
                    User newUser = new User(request.getUser().getUsername());
                    userRepository.save(newUser);
                    return newUser;
                });
        Recipe recipe = IRecipeService.createRecipe(request, user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(RecipeUpdateRequest request, Long recipeId) {
        Recipe recipes = getRecipeById(recipeId);
        Recipe theRecipe = IRecipeService.updateRecipe(recipes, request);
        return recipeRepository.save(theRecipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(()->
                new RecipeNotFoundException("Recipe not found with id: " + id));
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);

    }

    @Override
    public Set<String> getAllRecipeCategories() {
        return recipeRepository
                .findAll()
                .stream()
                .map(Recipe::getCategory)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllRecipeCuisines() {
        return recipeRepository
                .findAll()
                .stream()
                .map(Recipe::getCuisine)
                .collect(Collectors.toSet());
    }
    @Override
    public List<RecipeDto> getConvertedRecipes(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::convertToDto).toList();
    }

    @Override
    public RecipeDto convertToDto(Recipe recipe) {
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        UserDto userDto = modelMapper.map(recipe.getUser(), UserDto.class);
        Optional<Image> image = Optional.ofNullable(imageRepository.findByRecipeId(recipe.getId()));
        image.map(img -> modelMapper.map(img, ImageDto.class)).ifPresent(recipeDto::setImage);
        recipeDto.setUser(userDto);
        return recipeDto;
    }
}
