package com.geckotools.recipe.mapper;

import com.geckotools.recipe.dto.RecipeResponseDto;
import com.geckotools.recipe.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EntityToDtoMapper implements Function<Recipe, RecipeResponseDto> {

    @Override
    public RecipeResponseDto apply(Recipe recipe) {
        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getTimeRequiredToCook(),
                recipe.getTypeOfMeal()
        );
    }
}
