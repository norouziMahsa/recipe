package com.geckotools.recipe.mapper;

import com.geckotools.recipe.dto.RecipeRequestDto;
import com.geckotools.recipe.model.Recipe;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityMapper {

    public Recipe toEntity(RecipeRequestDto recipeRequestDto) {
        return new Recipe(
                recipeRequestDto.name(),
                recipeRequestDto.description(),
                recipeRequestDto.ingredients(),
                recipeRequestDto.timeRequiredToCook(),
                recipeRequestDto.typeOfMeal()
        );
    }
}
