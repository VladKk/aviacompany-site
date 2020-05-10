package aviacompany.config;

import aviacompany.extra.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    //    Створити Bean для кодера паролю
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    Розподілити сторінки за доступом
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests().antMatchers("/register")
                .not().fullyAuthenticated()
//                Для адміністратора
                .antMatchers("/admin/**", "/flights").hasRole("ADMIN")
//                Для користувача
                .antMatchers("/news").hasRole("USER")
//                Для усіх
                .antMatchers("/", "/home", "/about")
                .permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/home")
                .permitAll().and().logout().permitAll().logoutSuccessUrl("/home");
    }

    //    Зконфігурувати менеджера аутентифікації
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
