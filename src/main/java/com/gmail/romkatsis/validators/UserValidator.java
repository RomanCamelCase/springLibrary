package com.gmail.romkatsis.validators;

import com.gmail.romkatsis.models.User;
import com.gmail.romkatsis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        List<User> users = userService.findByName(user.getFullName());

        if (!users.isEmpty() && users.get(0).getId() != user.getId()) {
            errors.rejectValue("fullName", "", "We already have a user with this full name");
        }

        int currentYear = LocalDate.now().getYear();
        if (currentYear - user.getBirthYear()  < 18) {
            errors.rejectValue("birthYear", "", "You are too young");
        } else if (currentYear - user.getBirthYear()  > 100) {
            errors.rejectValue("birthYear", "", "You are too old");
        }
    }
}
