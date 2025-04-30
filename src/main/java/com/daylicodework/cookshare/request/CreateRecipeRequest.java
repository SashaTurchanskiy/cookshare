package com.daylicodework.cookshare.request;

import com.daylicodework.cookshare.model.Recipe;
import com.daylicodework.cookshare.model.User;
import lombok.Data;

@Data
public class CreateRecipeRequest {
    private Recipe recipe;
    private User user;

}
