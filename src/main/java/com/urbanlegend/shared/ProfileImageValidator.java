package com.urbanlegend.shared;

import com.urbanlegend.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProfileImageValidator implements ConstraintValidator<ProfileImage,String> {

    @Autowired
    FileService fileService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String fileType = fileService.detectType(value);
        if(fileType.equalsIgnoreCase("image/jpg")||fileType.equalsIgnoreCase("image/png")){
            return true;
        }
        return false;
    }
}
