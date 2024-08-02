package pl.edu.wszib.what.todo.notes.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.edu.wszib.what.todo.notes.model.User;
import pl.edu.wszib.what.todo.notes.session.SessionConstants;

import java.io.IOException;

public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession httpSession = req.getSession();

        if (httpSession == null || !(httpSession.getAttribute(SessionConstants.USER_KEY) instanceof User)) {
            res.sendRedirect("/");
        }
        chain.doFilter(request, response);
    }
}
