package com.daylicodework.cookshare.service.reviews;

import com.daylicodework.cookshare.request.ReviewRequest;

public interface IReviewService {

    void addReview(Long recipeId, ReviewRequest req);
    void deleteReview(Long recipeId, Long reviewId);
    int getTotalReview(Long recipeId);





}
