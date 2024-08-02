package pl.edu.wszib.what.todo.notes.validators;

import pl.edu.wszib.what.todo.notes.exceptions.RegisterValidationExemption;

public class RegisterValidator {
    public static void validateName(String name) {
        String nameSurnameRegex = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*$";
        if (!name.matches(nameSurnameRegex) || name.length() < 3 || name.length() > 57) {
            throw new RegisterValidationExemption();
        }
    }

    public static void validateSurname(String surname) {
        String nameSurnameRegex = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]*$";
        if (!surname.matches(nameSurnameRegex) || surname.length() < 2 || surname.length() > 35) {
            throw new RegisterValidationExemption();
        }
    }

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
