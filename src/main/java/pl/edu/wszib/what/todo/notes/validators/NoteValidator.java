package pl.edu.wszib.what.todo.notes.validators;


import pl.edu.wszib.what.todo.notes.exceptions.NoteValidationExeption;

public class NoteValidator {

    public static void validateTitle(String title) {
        String regex = "^(?!.* {3}).*$\n"; //Regex akceptujący dowolne znaki i nie akceptujący 3x spacji
        if (!title.matches(regex) && title.isBlank() && title.length() <= 30) {
            throw new NoteValidationExeption();
        }
    }

    public static void validateContent(String content) {
        if (content.isBlank() && content.length() <= 300) {
            throw new NoteValidationExeption();
        }
    }

    public static void validateStatus(String status) {
        if (status.isBlank() && status.length() <= 20) {
            throw new NoteValidationExeption();
        }
    }
}
