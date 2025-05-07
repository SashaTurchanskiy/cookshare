package com.daylicodework.cookshare.service.like;

import com.daylicodework.cookshare.exception.LikeNotFoundException;
import com.daylicodework.cookshare.model.Like;
import com.daylicodework.cookshare.model.Recipe;
import com.daylicodework.cookshare.repository.LikeRepository;
import com.daylicodework.cookshare.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService{

    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public Integer likeRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            if(!likeRepository.existsByRecipeId(recipe.getId())){
                Like like = new Like(recipe);
                likeRepository.save(like);
            }
            recipe.setLikeCount(recipe.getLikeCount() + 1);
            return recipeRepository.save(recipe).getLikeCount();
        }).orElseThrow(() -> new EntityNotFoundException("Recipe not found!"));
    }

    @Override
    public Integer unLikeRecipe(Long recipeId) throws LikeNotFoundException {
        return likeRepository.findFirstByRecipeId(recipeId).map(like -> {
            likeRepository.delete(like);
            Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
            if (recipe.getLikeCount() > 0) {
                recipe.setLikeCount(recipe.getLikeCount() - 1);
                recipeRepository.save(recipe);
            }else {
                throw new IllegalArgumentException("Like is already zero");
            }
            return recipeRepository.save(recipe).getLikeCount();
        }).orElseThrow(() -> new LikeNotFoundException("Like not found!"));
    }

    @Override
    public Long getLikeCount(Long recipeId) throws LikeNotFoundException {
        return likeRepository.findById(recipeId)
                .map(recipe -> likeRepository.countByRecipeId(recipe.getId())).orElse(0L);
    }
}
