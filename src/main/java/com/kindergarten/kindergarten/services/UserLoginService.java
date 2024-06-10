package com.kindergarten.kindergarten.services;

import com.kindergarten.kindergarten.common.security.UserDetailServiceImpl;
import com.kindergarten.kindergarten.enums.Role;
import com.kindergarten.kindergarten.model.dto.UserLoginDto;
import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.model.entity.Teacher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserLoginService {
    private final TeacherService teacherService;
    private final ParentService parentService;
    private final UserDetailServiceImpl userDetailService;


    public UserLoginService(TeacherService teacherService, ParentService parentService, UserDetailServiceImpl userDetailService) {
        this.teacherService = teacherService;
        this.parentService = parentService;
        this.userDetailService = userDetailService;
    }

    public boolean loginUser(UserLoginDto userLoginDto) {
        switch (userLoginDto.getRole().toUpperCase(Locale.ROOT)) {
            case "ADMIN" -> {
                return loginAdmin(userLoginDto);
            }
            case "TEACHER" -> {
                return loginTeacher(userLoginDto);
            }
            default -> {
                return loginParent(userLoginDto);
            }
        }
    }


//
    private boolean loginAdmin(UserLoginDto userLoginDto) {
        Teacher loginTeacher = new Teacher();
        if(isAdmin(userLoginDto.getEmail())){
            loginTeacher = new Teacher(userLoginDto.getEmail(), userLoginDto.getPassword(), Role.ADMIN);
        } else {
            return false;
        }

        UserDetails userDetails =
                userDetailService.loadUserByUsername(loginTeacher.getEmail());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (userDetails == null || (!encoder.matches(loginTeacher.getPassword(),userDetails.getPassword()))){
            return false;
        }

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);

        return true;
    }

    public boolean loginTeacher(UserLoginDto teacherLoginDto) {
        Teacher loginTeacher = new Teacher();
        if(isAdmin(teacherLoginDto.getEmail())){
            return false;
        } else {
            loginTeacher = new Teacher(teacherLoginDto.getEmail(), teacherLoginDto.getPassword());
        }

        UserDetails userDetails =
                userDetailService.loadUserByUsername(loginTeacher.getEmail());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (userDetails == null || (!encoder.matches(loginTeacher.getPassword(),userDetails.getPassword()))){
            return false;
        }

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);

        return true;
    }

    public boolean loginParent(UserLoginDto userDto) {
        Parent loginParent = new Parent(userDto.getEmail(), userDto.getPassword());

        UserDetails userDetails =
                userDetailService.loadUserByUsername(loginParent.getEmail());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (userDetails == null || (!encoder.matches(loginParent.getPassword(),userDetails.getPassword()))){
            return false;
        }

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);

        return true;
    }

    public boolean isAdmin(String email) {
        return email.contains("@childo."); //TODO change after name to
    }
}
