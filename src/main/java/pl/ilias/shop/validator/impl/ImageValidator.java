package pl.ilias.shop.validator.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.ilias.shop.validator.ImageValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class ImageValidator implements ConstraintValidator<ImageValid, MultipartFile> {
    Set<String> imageExtension = Set.of("jpg", "png", "bmp", "gif");
    //toDO pobrac zmienne z yamla

    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        return FilenameUtils.isExtension(image.getOriginalFilename(), imageExtension);
    }
}
