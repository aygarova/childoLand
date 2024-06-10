package com.kindergarten.kindergarten.configuration;

import com.kindergarten.kindergarten.common.security.UserDetailServiceImpl;
import com.kindergarten.kindergarten.enums.Role;
import com.kindergarten.kindergarten.repositories.ParentRepository;
import com.kindergarten.kindergarten.repositories.TeacherRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurator extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailService;

    public SecurityConfigurator(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/login", "/register", "/parent-register", "/teacher-register", "/start").permitAll()
                .antMatchers("/logout").hasAnyRole(Role.TEACHER.name(),Role.PARENT.name(), Role.ADMIN.name())
                .antMatchers("/teacher/**", "/saveEvent").hasRole(Role.TEACHER.name())
                .antMatchers("/parent/**").hasRole(Role.PARENT.name())
                .antMatchers("/admin/**", "/admin/addChild", "admin/groups-add", "admin/groups", "/children/register").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login")
                .and()
                .logout().clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("time")
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public UserDetailsService userDetailsService(TeacherRepository teacherRepository, ParentRepository parentRepository) {
        return new UserDetailServiceImpl(teacherRepository, parentRepository);
    }
}