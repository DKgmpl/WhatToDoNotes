package pl.edu.wszib.what.todo.notes.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity (name = "tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN,
        USER
    }

    public User(Long id) {
        this.id = id;
    }
}
