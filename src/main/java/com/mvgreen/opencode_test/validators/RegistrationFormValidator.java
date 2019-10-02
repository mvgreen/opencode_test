package com.mvgreen.opencode_test.validators;

import com.mvgreen.opencode_test.entities.UserData;
import com.mvgreen.opencode_test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserData user = (UserData) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "EmptyField");
        if (errors.hasErrors())
            return;
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "EmptyField");
        if (errors.hasFieldErrors("password"))
            return;
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userForm.confirmPassword");
        }
    }
}
