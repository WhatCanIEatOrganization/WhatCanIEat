package com.recipeservice.repository;

import com.recipeservice.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByFavorite(boolean bool);


    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.preparationSteps LEFT JOIN FETCH r.tags WHERE r.id = :id")
    Optional<Recipe> findByIdWithRelations(@Param("id") Integer id);


    @Query("SELECT r FROM Recipe r JOIN FETCH r.preparationSteps JOIN FETCH r.tags")
    List<Recipe> findAllWithRelations();


    @Query(value = "SELECT recipe_id\n" +
            "FROM recipe_tags\n" +
            "WHERE tag IN (:tags)\n" +
            "GROUP BY recipe_id\n" +
            "HAVING COUNT(DISTINCT tag) = :count", nativeQuery = true)
    List<Integer> findRecipesIdsByTags(@Param("tags") List<String> tags, @Param("count") long count);

}
