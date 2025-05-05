package com.daylicodework.cookshare.service.image;

import com.daylicodework.cookshare.dto.ImageDto;
import com.daylicodework.cookshare.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImage(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    ImageDto saveImage(MultipartFile file, Long recipeId);
}
