package com.daylicodework.cookshare.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.daylicodework.cookshare.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
