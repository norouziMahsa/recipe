package com.geckotools.recipe.exception;


import org.springframework.http.HttpStatus;

public record ApiError(String message, HttpStatus status) {
}
