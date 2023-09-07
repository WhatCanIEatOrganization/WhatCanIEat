package com.recipeservice.repository;

import com.recipeservice.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByFavorite(boolean bool);

    @Query(value = "SELECT recipe_id\n" +
            "FROM recipe_tags\n" +
            "WHERE tag IN (:tags)\n" +
            "GROUP BY recipe_id\n" +
            "HAVING COUNT(DISTINCT tag) = :count", nativeQuery = true)
    List<Integer> findRecipeIdsByTags(@Param("tags") List<String> tags, @Param("count") long count);

}
