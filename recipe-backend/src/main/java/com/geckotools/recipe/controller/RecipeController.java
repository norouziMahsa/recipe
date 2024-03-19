package com.geckotools.recipe.controller;

import com.geckotools.recipe.dto.RecipeRequestDto;
import com.geckotools.recipe.dto.RecipeResponseDto;
import com.geckotools.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "Add a new recipe")
    @PostMapping("/add")
    public ResponseEntity<RecipeResponseDto> add(@RequestBody RecipeRequestDto recipeRequestDto) {
        log.debug("hit post /recipe/add, recipe name {}", recipeRequestDto.name());
        return new ResponseEntity<>(recipeService.add(recipeRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve a recipe by id")
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> findById(@PathVariable("id") long id) {
        log.debug("hit get /recipe/{}", id);
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @Operation(
            summary = "Retrieve all recipes" ,
            description = "Retrieve all recipes by pagination, default page is 0 and default page size is 5"
    )
    @GetMapping("/all")
    public ResponseEntity<Page<RecipeResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.debug("hit get /recipe/all");
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(recipeService.findAll(pageable));
    }

    @Operation(
            summary = "Update an existing recipe",
            description = "Update an existing recipe by id," +
                    " if no recipe can be found by this id," +
                    " it throws and exception"
    )
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> update(@RequestBody RecipeRequestDto recipeRequestDto, @PathVariable long id) {
        log.debug("hit put /recipe/{}", id);
      return ResponseEntity.ok(recipeService.update(recipeRequestDto, id));
    }

    @Operation(
            summary = "Search recipes by name",
            description = "It searches all recipes that their names contain the search term"
    )
    @GetMapping("/search")
    public ResponseEntity<Page<RecipeResponseDto>> search(
            @RequestParam("name") String recipeName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        log.debug("hit /recipe/search by recipe name {}", recipeName);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(recipeService.search(recipeName, pageable));
    }
}
