package pl.edu.wszib.what.todo.notes.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Note {
    private int id;
    private String title;
    private String content;
    private String status;
}
