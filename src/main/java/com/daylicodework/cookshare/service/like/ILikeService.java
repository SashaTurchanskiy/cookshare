package com.daylicodework.cookshare.service.like;

import com.daylicodework.cookshare.exception.LikeNotFoundException;

public interface ILikeService {
    Integer likeRecipe(Long recipeId);
    Integer unLikeRecipe(Long recipeId) throws LikeNotFoundException;
    Long getLikeCount(Long recipeId) throws LikeNotFoundException;

}
