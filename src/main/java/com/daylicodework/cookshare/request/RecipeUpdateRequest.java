package com.daylicodework.cookshare.request;

import lombok.Data;

import java.util.List;

@Data
public class RecipeUpdateRequest {
    private String title;
    private String instructions;
    private String prepTime;
    private String cookTime;
    private String category;
    private String description;
    private String cuisine;
    private List<String> ingredients;
}
