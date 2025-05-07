package com.daylicodework.cookshare.dto;

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
    private int likeCount;
    private UserDto user;
    private ImageDto image;
}
