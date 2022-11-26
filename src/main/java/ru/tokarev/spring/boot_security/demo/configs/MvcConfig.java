package ru.tokarev.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry reg) {
        reg.addViewController("/admin").setViewName("admin/admin");
        reg.addViewController("/user").setViewName("user/user");
        reg.addViewController("/login").setViewName("login/loginPage");
    }
}
