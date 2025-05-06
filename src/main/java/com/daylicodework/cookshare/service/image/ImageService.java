package com.daylicodework.cookshare.service.image;

import com.daylicodework.cookshare.dto.ImageDto;
import com.daylicodework.cookshare.exception.ImageNotFoundException;
import com.daylicodework.cookshare.model.Image;
import com.daylicodework.cookshare.model.Recipe;
import com.daylicodework.cookshare.repository.ImageRepository;
import com.daylicodework.cookshare.service.recipe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final RecipeService recipeService;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId).orElseThrow(()->
                new ImageNotFoundException("Image not found with id: " + imageId));
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, ()->{
            throw new ImageNotFoundException("Image not found with id: " + imageId);
        });


    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch (IOException | SQLException e ){
           throw new RuntimeException(e);
        }

    }

    @Override
    public ImageDto saveImage(MultipartFile file, Long recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        try {
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setRecipe(recipe);

            Image savedImage = imageRepository.save(image);

            String buildDownloadUrl = "/api/images/image/download/" + savedImage.getId();
            image.setDownloadUrl(buildDownloadUrl);
            imageRepository.save(savedImage);

//            String downloadUrl = "/api/images/image/download/" + image.getId();
//            image.setDownloadUrl(downloadUrl);
//
//            Image savedImage = imageRepository.save(image);
//            savedImage.setDownloadUrl(downloadUrl + savedImage.getId());
//            imageRepository.save(savedImage);

            ImageDto imageDto = new ImageDto();
            imageDto.setId(savedImage.getId());
            imageDto.setFileName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());

            return imageDto;

        }catch (IOException | SQLException e ){
            throw new RuntimeException(e);
        }
    }
}
