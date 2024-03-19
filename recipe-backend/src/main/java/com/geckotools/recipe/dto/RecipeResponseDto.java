package com.geckotools.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geckotools.recipe.model.Ingredient;
import com.geckotools.recipe.model.TypeOfMeal;

import java.util.Set;

public record RecipeResponseDto(
        @JsonProperty("id")
        long id,
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("ingredients")
        Set<Ingredient> ingredients,
        @JsonProperty("timeRequiredToCook")
        long timeRequiredToCook,
        @JsonProperty("typeOfMeal")
        TypeOfMeal typeOfMeal
) { }
