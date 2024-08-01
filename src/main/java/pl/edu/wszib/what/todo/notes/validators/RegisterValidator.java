package pl.edu.wszib.what.todo.notes.validators;

import pl.edu.wszib.what.todo.notes.exceptions.RegisterValidationExemption;

public class RegisterValidator {
    public static void validateLogin (String login) {
        String regex = "^[a-zA-Z0-9.]*$";
        if (!login.matches(regex) || login.length() < 4 || login.length() > 32) {
            throw new RegisterValidationExemption();
        }
    }

    public static void validatePassword (String password) {
        if (password == null || password.length() < 4 || password.length() > 64) {
            throw new RegisterValidationExemption();
        }
    }
}
