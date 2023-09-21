package com.gmail.romkatsis.validators;

import com.gmail.romkatsis.dao.UserDAO;
import com.gmail.romkatsis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class UserValidator implements Validator {
    private final UserDAO userDAO;

    @Autowired
    public UserValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userDAO.getUser(user.getFullName()).isPresent()) {
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
