package pl.edu.wszib.what.todo.notes.config;

import org.hibernate.SessionFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.edu.wszib.what.todo.notes.filters.UserFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan("pl.edu.wszib.what.todo.notes")
public class AppConfig {

    @Bean
    public FilterRegistrationBean<UserFilter> userFilter() {
        FilterRegistrationBean<UserFilter> registrationBean  = new FilterRegistrationBean<>();

        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns("/note/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public Connection jdbcConnection() {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:mem:testdb","root","");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "entityManagerFactory")
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }
}
