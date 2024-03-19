package com.geckotools.recipe.controller;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SqlGroup({
        @Sql(value = "classpath:init/data.sql", executionPhase = BEFORE_TEST_CLASS)
})
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_retrieve_all_recipes() throws Exception {
        this.mockMvc.perform(get("/recipe/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].name").value("recipe1"));
    }

    @Test
    void should_find_recipe_by_id() throws Exception {
        this.mockMvc.perform(get("/recipe/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("recipe1"));
    }

    @Test
    void should_search_recipe_by_partial_name() throws Exception {
        var searchTerm = "rec";
        this.mockMvc.perform(
                get("/recipe/search").param("name", searchTerm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].name").value("recipe1"));
    }

    @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    @Test
    void should_add_new_recipe() throws Exception {
        final var jsonFile = new ClassPathResource("init/recipe_dto_add_new.json").getFile();
        final var recipe = Files.readString(jsonFile.toPath());
        this.mockMvc.perform(
                        post("/recipe/add")
                                .contentType(APPLICATION_JSON)
                                .content(recipe)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("test_add_new_recipe"));
    }

    @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD)
    @Test
    void should_update_existing_recipe() throws Exception {
        final var recipeJsonFile = new ClassPathResource("init/recipe_dto_update_existing.json").getFile();
        final var recipe = Files.readString(recipeJsonFile.toPath());
        this.mockMvc.perform(
                post("/recipe/add")
                        .contentType(APPLICATION_JSON)
                        .content(recipe)
        ).andExpect(status().isCreated());

        final var updatedRecipeJsonFile = new ClassPathResource("init/updated_recipe.json").getFile();
        final var updatedRecipe = Files.readString(updatedRecipeJsonFile.toPath());
        this.mockMvc.perform(
                        put("/recipe/{id}", 1)
                                .contentType(APPLICATION_JSON)
                                .content(updatedRecipe)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("test_update_existing_recipe"));
    }
}
