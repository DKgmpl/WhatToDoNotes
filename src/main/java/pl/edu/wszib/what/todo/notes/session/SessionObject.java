package pl.edu.wszib.what.todo.notes.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.edu.wszib.what.todo.notes.model.User;

@Component
@SessionScope
@Getter
@Setter
public class SessionObject {
    User user;
    String info;
    int cos;
}
