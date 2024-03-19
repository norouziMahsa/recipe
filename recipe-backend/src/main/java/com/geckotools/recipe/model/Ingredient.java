package com.geckotools.recipe.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredient")
@Data
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private int amount;

    public Ingredient(long id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}
