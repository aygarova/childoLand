package com.kindergarten.kindergarten.common.security;

import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.model.entity.Teacher;
import com.kindergarten.kindergarten.repositories.ParentRepository;
import com.kindergarten.kindergarten.repositories.TeacherRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Configuration
public class UserDetailServiceImpl implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;

    public UserDetailServiceImpl(TeacherRepository teacherRepository, ParentRepository parentRepository) {
        this.teacherRepository = teacherRepository;
        this.parentRepository = parentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByEmail(username).orElse(null);
        Parent parent = parentRepository.findByEmail(username).orElse(null);
        if (teacher == null) {
            if (parent == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new CustomUserDetails(
                    parent.getEmail(),
                    parent.getPassword(),
                    parent.getFirstName(),
                    parent.getLastName(),
                    List.of(new SimpleGrantedAuthority("ROLE_PARENT"))
            );
        }

        return new CustomUserDetails(
                teacher.getEmail(),
                teacher.getPassword(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getRole().name().equals("ADMIN") ?
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) :
                        List.of(new SimpleGrantedAuthority("ROLE_TEACHER"))
        );
    }
}
