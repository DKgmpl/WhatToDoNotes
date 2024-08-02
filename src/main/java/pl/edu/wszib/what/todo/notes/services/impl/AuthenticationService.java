package pl.edu.wszib.what.todo.notes.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.edu.wszib.what.todo.notes.dao.impl.IUserDAO;
import pl.edu.wszib.what.todo.notes.model.User;
import pl.edu.wszib.what.todo.notes.services.IAuthenticationService;
import pl.edu.wszib.what.todo.notes.session.SessionConstants;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserDAO userDAO;
    private final HttpSession httpSession;


    public AuthenticationService(IUserDAO userDAO, HttpSession httpSession) {
        this.userDAO = userDAO;
        this.httpSession = httpSession;
    }

    @Override
    public void login(String login, String password) {
        Optional<User> user = this.userDAO.getByLogin(login);
        if (user.isPresent() &&
                DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.get().getPassword())) {
            httpSession.setAttribute(SessionConstants.USER_KEY, user.get());
            System.out.println("Logged in");
            return;
        }
        this.httpSession.setAttribute("loginInfo", "Złe dane");
        System.out.println("ERR.. Not logged in, try again");
    }

    @Override
    public void register(String name, String surname, String login, String password) {
        Optional<User> user = this.userDAO.getByLogin(login);
        User newUser = new User();
        if (user.isEmpty()) {
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setLogin(login);
            newUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            newUser.setRole(User.Role.USER);
            this.userDAO.save(newUser);
            System.out.println("Registered new user");
            return;
        }
        this.httpSession.setAttribute("registerInfo", "Złe dane");
        System.out.println("ERR.. Not registered new user, try again");
    }

    @Override
    public void logout() {
        this.httpSession.removeAttribute(SessionConstants.USER_KEY);
        System.out.println("Logged out" + this.userDAO.getByLogin(toString()));
    }

    @Override
    public String getLoginInfo() {
        String tempGetLoginInfo = (String) this.httpSession.getAttribute("loginInfo");
        this.httpSession.removeAttribute("loginInfo");
        return tempGetLoginInfo;
    }

    @Override
    public String getRegisterInfo() {
        String tempGetRegisterInfo = (String) this.httpSession.getAttribute("registerInfo");
        this.httpSession.removeAttribute("registerInfo");
        return tempGetRegisterInfo;
    }
}
