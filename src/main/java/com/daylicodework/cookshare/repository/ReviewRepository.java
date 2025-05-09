package com.daylicodework.cookshare.repository;

import com.daylicodework.cookshare.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    int countALlByRecipeId(Long recipeId);

    Optional<Review> findByRecipeIdAndUserId(Long recipeId, Long userId);
}
