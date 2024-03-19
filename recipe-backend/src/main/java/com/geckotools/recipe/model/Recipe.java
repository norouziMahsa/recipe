package com.geckotools.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipe")
@NoArgsConstructor
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(
            targetEntity = Ingredient.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JoinColumn(name = "recipe_ingredient_id", referencedColumnName = "id")
    private Set<Ingredient> ingredients = new HashSet<>();;

    @Column(name = "time_required_to_cook")
    @JsonProperty("timeRequiredToCook")
    private long timeRequiredToCook;

    @Column(name = "type_of_meal")
    @Enumerated(EnumType.STRING)
    @JsonProperty("typeOfMeal")
    private TypeOfMeal typeOfMeal;

    public Recipe(
            String name,
            String description,
            Set<Ingredient> ingredients,
            long timeRequiredToCook,
            TypeOfMeal typeOfMeal
    ) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.timeRequiredToCook = timeRequiredToCook;
        this.typeOfMeal = typeOfMeal;
    }
}
