package pl.edu.wszib.what.todo.notes.services;

public interface IAuthenticationService {

    void login(String login, String password);
    void register(String name, String surname, String login, String password);
    void logout();

    String getLoginInfo();
    String getRegisterInfo();
}
