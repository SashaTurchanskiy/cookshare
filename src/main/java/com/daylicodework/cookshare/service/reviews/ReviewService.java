package com.daylicodework.cookshare.service.reviews;

import com.daylicodework.cookshare.model.Recipe;
import com.daylicodework.cookshare.model.Review;
import com.daylicodework.cookshare.model.User;
import com.daylicodework.cookshare.repository.RecipeRepository;
import com.daylicodework.cookshare.repository.ReviewRepository;
import com.daylicodework.cookshare.repository.UserRepository;
import com.daylicodework.cookshare.request.ReviewRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Override
    public void addReview(Long recipeId, ReviewRequest req) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(()-> new EntityNotFoundException("Recipe not found!"));

        User user = userRepository.findById(req.getUserId()).orElseThrow(()->
                new EntityNotFoundException("User not found!"));

        reviewRepository.findByRecipeIdAndUserId(recipeId, req.getUserId())
                .ifPresentOrElse((existingReview -> updateReview(existingReview, req)),
                () -> createNewReview(req, recipe));

        double averageRating = recipe.calculateAverageRating();
        recipe.setAverageRating(averageRating);
        int totalRateCount = recipe.getTotalRateCount();
        recipe.setTotalRateCount(totalRateCount);
        recipeRepository.save(recipe);

    }

    private void updateReview(Review existingReview, ReviewRequest req) {
        existingReview.setStars(req.getStars());
        existingReview.setFeedback(req.getFeedback());

        reviewRepository.save(existingReview);
    }

    private void createNewReview(ReviewRequest req, Recipe recipe) {
        User user = userRepository.findById(req.getUserId()).orElseThrow(()->
                new EntityNotFoundException("User not found!"));
        Review review = new Review();
        review.setUser(user);
        review.setStars(req.getStars());
        review.setFeedback(req.getFeedback());
        review.setRecipe(recipe);
        review.setUser(user);
        reviewRepository.save(review);
    }



    @Override
    public void deleteReview(Long recipeId, Long reviewId) {
        reviewRepository.findById(reviewId).ifPresentOrElse(review ->{
            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(()-> new EntityNotFoundException("Recipe not found!"));
            recipe.getReview().remove(review);
            recipeRepository.save(recipe);

            reviewRepository.delete(review);
        },()->{
            throw new EntityNotFoundException("Review not found!");
        });


    }

    @Override
    public int getTotalReview(Long recipeId) {
        return reviewRepository.countALlByRecipeId(recipeId);

    }
}
