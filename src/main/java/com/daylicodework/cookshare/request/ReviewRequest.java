package com.daylicodework.cookshare.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long userId;
    private int stars;
    private String feedback;


}
