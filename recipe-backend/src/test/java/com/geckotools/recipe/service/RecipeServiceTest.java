package com.geckotools.recipe.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.geckotools.recipe.dto.RecipeRequestDto;
import com.geckotools.recipe.dto.RecipeResponseDto;
import com.geckotools.recipe.mapper.DtoToEntityMapper;
import com.geckotools.recipe.mapper.EntityToDtoMapper;
import com.geckotools.recipe.model.Recipe;
import com.geckotools.recipe.repository.RecipeRepository;
import com.geckotools.recipe.service.impl.RecipeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class RecipeServiceTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    DtoToEntityMapper dtoToEntityMapper;

    @Mock
    EntityToDtoMapper entityToDtoMapper;

    @Test
    public void should_add_new_recipe() throws IOException {
        // Given
        final var recipeJsonFile = new ClassPathResource("init/recipe.json").getFile();
        final var expectedRecipe = new ObjectMapper().readValue(recipeJsonFile, Recipe.class);

        final var recipeDtoJsonFile = new ClassPathResource("init/recipe_dto_add_new.json").getFile();
        final var recipeRequestDto = new ObjectMapper().readValue(recipeDtoJsonFile, RecipeRequestDto.class);

        final var expectedResponseDto = new RecipeResponseDto(
                expectedRecipe.getId(),
                expectedRecipe.getName(),
                expectedRecipe.getDescription(),
                expectedRecipe.getIngredients(),
                expectedRecipe.getTimeRequiredToCook(),
                expectedRecipe.getTypeOfMeal()
        );

        when(dtoToEntityMapper.toEntity(recipeRequestDto)).thenReturn(expectedRecipe);
        when(recipeRepository.save(any())).thenReturn(expectedRecipe);
        when(entityToDtoMapper.apply(any())).thenReturn(expectedResponseDto);

        // When
        final var addedRecipe = recipeService.add(recipeRequestDto);

        // Then
        assertEquals(expectedRecipe.getId(), addedRecipe.id());
        assertEquals(expectedRecipe.getName(), addedRecipe.name());
    }

    @Test
    public void should_find_recipe_by_id() throws IOException {
        // Given
        final var recipeJsonFile = new ClassPathResource("init/recipe.json").getFile();
        final var recipe = new ObjectMapper().readValue(recipeJsonFile, Recipe.class);
        final var expectedRecipeTobeFound = new RecipeResponseDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getTimeRequiredToCook(),
                recipe.getTypeOfMeal()
        );

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(entityToDtoMapper.apply(any())).thenReturn(expectedRecipeTobeFound);

        // When
        var foundRecipe = recipeService.findById(recipe.getId());

        // Then
        assertEquals(expectedRecipeTobeFound.id(), foundRecipe.id());
        assertEquals(expectedRecipeTobeFound.name(), foundRecipe.name());
    }

    @Test
    public void should_update_existing_recipe() throws IOException {
        // Given
        final var recipeJsonFile = new ClassPathResource("init/recipe.json").getFile();
        final var existingRecipeToBeUpdated = new ObjectMapper().readValue(recipeJsonFile, Recipe.class);

        final var recipeDtoJsonFile = new ClassPathResource("init/recipe_dto_update_existing.json").getFile();
        final var recipeRequestDto = new ObjectMapper().readValue(recipeDtoJsonFile, RecipeRequestDto.class);

        final var expectedRecipeAfterUpdate = new RecipeResponseDto(
                existingRecipeToBeUpdated.getId(),
                existingRecipeToBeUpdated.getName(),
                existingRecipeToBeUpdated.getDescription(),
                existingRecipeToBeUpdated.getIngredients(),
                existingRecipeToBeUpdated.getTimeRequiredToCook(),
                existingRecipeToBeUpdated.getTypeOfMeal()
        );

        when(recipeRepository.findById(any())).thenReturn(Optional.of(existingRecipeToBeUpdated));
        when(dtoToEntityMapper.toEntity(any())).thenReturn(existingRecipeToBeUpdated);
        when(recipeRepository.save(any())).thenReturn(existingRecipeToBeUpdated);
        when(entityToDtoMapper.apply(any())).thenReturn(expectedRecipeAfterUpdate);

        // When
        var actualRecipeAfterUpdate = recipeService.update(recipeRequestDto, existingRecipeToBeUpdated.getId());

        // Then
        assertEquals(expectedRecipeAfterUpdate, actualRecipeAfterUpdate);
    }
}
