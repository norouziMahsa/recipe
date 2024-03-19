package com.geckotools.recipe.service.impl;

import com.geckotools.recipe.dto.RecipeRequestDto;
import com.geckotools.recipe.dto.RecipeResponseDto;
import com.geckotools.recipe.exception.RecipeNotFoundException;
import com.geckotools.recipe.mapper.DtoToEntityMapper;
import com.geckotools.recipe.mapper.EntityToDtoMapper;
import com.geckotools.recipe.repository.RecipeRepository;
import com.geckotools.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final DtoToEntityMapper dtoToEntityMapper;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public RecipeResponseDto add(RecipeRequestDto recipeRequestDto) {
        var addedRecipe = recipeRepository.save(dtoToEntityMapper.toEntity(recipeRequestDto));
        log.debug("new recipe by recipe id {} is added", addedRecipe.getId());
        return entityToDtoMapper.apply(addedRecipe);
    }

    @Override
    public RecipeResponseDto findById(long id) {
        return recipeRepository
                .findById(id)
                .map(entityToDtoMapper)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found for id : " + id));
    }

    @Override
    public Page<RecipeResponseDto> findAll(Pageable pageable) {
        return recipeRepository
                .findAll(pageable)
                .map(entityToDtoMapper);
    }

    @Override
    public RecipeResponseDto update(RecipeRequestDto recipeRequestDto, long id) {
        if (recipeRepository.findById(id).isPresent()) {
            var recipe = dtoToEntityMapper.toEntity(recipeRequestDto);
            recipe.setId(id);
            var updatedRecipe = recipeRepository.save(recipe);
            log.debug("recipe by recipe id {} is updated", updatedRecipe.getId());
            return entityToDtoMapper.apply(updatedRecipe);
        } else {
            throw new RecipeNotFoundException("Recipe not found for id : " + id);
        }
    }

    @Override
    public Page<RecipeResponseDto> search(String recipeName, Pageable pageable) {
        return recipeRepository
                .findByNameContaining(recipeName, pageable)
                .map(entityToDtoMapper);
    }
}
