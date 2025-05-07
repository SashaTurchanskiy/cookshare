package com.daylicodework.cookshare.controller;

import com.daylicodework.cookshare.exception.LikeNotFoundException;
import com.daylicodework.cookshare.service.like.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final ILikeService likeService;

    @PostMapping("/recipe/{recipeId}/like")
    public ResponseEntity<Integer> likeRecipe(@PathVariable Long recipeId) {
        int like = likeService.likeRecipe(recipeId);
        return ResponseEntity.ok(like);
    }
    @PutMapping("/recipe/{recipeId}/unlike")
    public ResponseEntity<Integer> unLikeRecipe(@PathVariable Long recipeId) throws LikeNotFoundException {
        int like = likeService.unLikeRecipe(recipeId);
        return ResponseEntity.ok(like);
    }
    @GetMapping("/recipe/{recipeId}/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long recipeId) throws LikeNotFoundException {
        Long count = likeService.getLikeCount(recipeId);
        return ResponseEntity.ok(count);
    }
}
