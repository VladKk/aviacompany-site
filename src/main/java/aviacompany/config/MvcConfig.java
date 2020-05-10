package aviacompany.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/").setViewName("home");
        viewControllerRegistry.addViewController("/home").setViewName("home");
        viewControllerRegistry.addViewController("/about").setViewName("about");
        viewControllerRegistry.addViewController("/login").setViewName("login");
        viewControllerRegistry.addViewController("/news").setViewName("news");
    }
}
