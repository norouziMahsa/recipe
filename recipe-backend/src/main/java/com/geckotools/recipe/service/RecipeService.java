package com.geckotools.recipe.service;

import com.geckotools.recipe.dto.RecipeRequestDto;
import com.geckotools.recipe.dto.RecipeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {

    RecipeResponseDto add(RecipeRequestDto recipeRequestDto);

    RecipeResponseDto findById(long id);

    Page<RecipeResponseDto> findAll(Pageable pageable);

    RecipeResponseDto update(RecipeRequestDto recipeRequestDto, long id);

    Page<RecipeResponseDto> search(String recipeName, Pageable pageable);

//    List<RecipeDto> search(Predicate predicate);
}
