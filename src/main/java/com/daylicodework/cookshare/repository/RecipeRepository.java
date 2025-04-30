package com.daylicodework.cookshare.repository;

import com.daylicodework.cookshare.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
