package pl.edu.wszib.what.todo.notes.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.edu.wszib.what.todo.notes.filters.UserFilter;

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
}
