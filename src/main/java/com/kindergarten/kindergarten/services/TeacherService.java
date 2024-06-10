package com.kindergarten.kindergarten.services;

import com.kindergarten.kindergarten.common.convertor.ModelConvertor;
import com.kindergarten.kindergarten.common.security.UserDetailServiceImpl;
import com.kindergarten.kindergarten.enums.Role;
import com.kindergarten.kindergarten.exceptions.ExceptionMessages;
import com.kindergarten.kindergarten.exceptions.WrongActionException;
import com.kindergarten.kindergarten.model.dto.GroupDto;
import com.kindergarten.kindergarten.model.dto.UserLoginDto;
import com.kindergarten.kindergarten.model.dto.TeacherRegistrationDto;
import com.kindergarten.kindergarten.model.entity.Group;
import com.kindergarten.kindergarten.model.entity.Teacher;
import com.kindergarten.kindergarten.repositories.TeacherRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelConvertor modelConvertor;
    private final UserDetailServiceImpl userDetailService;

    private final PasswordEncoder passwordEncoder;
    public final ExceptionMessages exceptionMessages;

    private String adminEmail = System.getenv("ADMIN_EMAIL");
    private String adminPass = System.getenv("ADMIN_PASS");


    public TeacherService(TeacherRepository userRepository, ModelConvertor modelConvertor, UserDetailServiceImpl userDetailService, PasswordEncoder passwordEncoder, ExceptionMessages exceptionMessages) {
        this.teacherRepository = userRepository;
        this.modelConvertor = modelConvertor;
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.exceptionMessages = exceptionMessages;
    }

    public void registerUser(TeacherRegistrationDto teacherRegistrationDto) {
        Teacher teacher = modelConvertor.fromDtoToEntity(teacherRegistrationDto, passwordEncoder);
        if (isAdmin(teacher.getEmail())){
            throw new WrongActionException(exceptionMessages.getAlreadyExsistUser());
        }
        Teacher usersWithThisEmail = teacherRepository.findByEmail(teacher.getEmail()).orElse(null);
        if (!Objects.isNull(usersWithThisEmail)){
            throw new WrongActionException(exceptionMessages.getAlreadyExsistUser());
        }

        teacherRepository.save(teacher);

    }

    public boolean loginUser(UserLoginDto teacherLoginDto) {
        Teacher loginTeacher = new Teacher();
        if(isAdmin(teacherLoginDto.getEmail())){
             loginTeacher = new Teacher(teacherLoginDto.getEmail(), teacherLoginDto.getPassword(), Role.ADMIN);
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

    public Teacher findTeacher(GroupDto groupDto) {
        return teacherRepository.findByEmail(groupDto.getTeacherEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid teacher email:" + groupDto.getTeacherEmail()));
    }

    public Teacher findTeacher(String group) {
       return teacherRepository.findAll().stream().filter(teacher -> teacher.getGroup().getName().equals(group)).collect(Collectors.toList()).get(0);
    }

    public Teacher findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid teacher email:" + email));
    }

    public void saveGroup(Group group){
        Teacher teacher = teacherRepository.findByEmail(group.getTeacher().getEmail()).get();
        teacherRepository.save(
                new Teacher(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getPassword(),
                        teacher.getPhoneNumber(), teacher.getEmail(), teacher.getRole(), group)
        );
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void initAdmin() {
        List<Teacher> admins = new ArrayList<>();
        List<Teacher> teachers = this.teacherRepository.findAll();

        teachers.forEach(teacher -> {
            if (teacher.getRole().equals(Role.ADMIN)) {
                admins.add(teacher);
            }
        });

        if (admins.size() == 0) {
            Teacher admin = new Teacher(
                    "Admin",
                    "Adminov",
                    passwordEncoder.encode(adminPass),
                    "phoneNumber",
                    adminEmail,
                    Role.ADMIN
            );
            this.teacherRepository.save(admin);
        }
    }

    public boolean isAdmin(String email) {
        return email.contains("@childo."); //TODO change after name to
    }
}
