package com.geckotools.recipe.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geckotools.recipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        recipeRepository
                .findAll()
                .forEach(entity -> recipeRepository.delete(entity));
    }

    @Test
    public void should_add_new_recipe() throws IOException {
        // Given
        final var recipeName = "test_add_recipe";
        final var jsonFile = new ClassPathResource("init/recipe.json").getFile();
        final var recipe = new ObjectMapper().readValue(jsonFile, Recipe.class);

        // When
        final var dbRecipe = recipeRepository.save(recipe);

        // Then
        assertNotNull(dbRecipe);
        assertEquals(recipeName, dbRecipe.getName());
    }

    @Test
    public void should_find_recipe_by_id() throws IOException {
        // Given
        final var recipeJsonFile = new ClassPathResource("init/recipe.json").getFile();
        final var recipe = new ObjectMapper().readValue(recipeJsonFile, Recipe.class);
        final var existingRecipe = recipeRepository.save(recipe);

        // When
        Optional<Recipe> dbRecipe = recipeRepository.findById(existingRecipe.getId());

        // Then
        assertTrue(dbRecipe.isPresent());
        assertEquals(existingRecipe.getId(), dbRecipe.get().getId());
    }
}
