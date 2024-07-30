package pl.edu.wszib.what.todo.notes.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Role role;

    public enum Role {
        ADMIN,
        USER
    }
}
