package com.daylicodework.cookshare.dto;

import com.daylicodework.cookshare.model.User;
import lombok.Data;

@Data
public class RecipeDto {
    private Long id;
    private String title;
    private String instructions;
    private String prepTime;
    private String cookTime;
    private String category;
    private String description;
    private String cuisine;

    private UserDto user;
}
