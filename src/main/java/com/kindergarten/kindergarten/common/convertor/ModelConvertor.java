package com.kindergarten.kindergarten.common.convertor;

import com.kindergarten.kindergarten.common.CurrentUsers;
import com.kindergarten.kindergarten.enums.Gender;
import com.kindergarten.kindergarten.enums.Role;
import com.kindergarten.kindergarten.model.dto.*;
import com.kindergarten.kindergarten.model.entity.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ModelConvertor {

    private final CurrentUsers currentUsers;

    public ModelConvertor(CurrentUsers currentUsers) {
        this.currentUsers = currentUsers;
    }

    public Teacher fromDtoToEntity(TeacherRegistrationDto dto, PasswordEncoder passwordEncoder) {
        return new Teacher(
                dto.getFirstName(),
                dto.getLastName(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getPhoneNumber(),
                dto.getEmail(),
                Role.TEACHER
        );
    }

    public Parent fromDtoToEntity(ParentRegistrationDto dto, PasswordEncoder passwordEncoder) {
        return new Parent(
                dto.getFirstName(),
                dto.getLastName(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getPhoneNumber(),
                dto.getEmail(),
                dto.getIsActive(),
                dto.getChildEGN(),
                Role.PARENT
        );
    }
    public Child fromDtoToEntity(ChildRegisterDto dto) {
        return new Child(
                dto.getEgn(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAge(),
                new Group(dto.getGroup()),
                dto.getGender().equals("BOY") ? Gender.BOY: Gender.GIRL,
                dto.getParentsEmails()
        );
    }

    public Child fromDtoToEntity(ChildRegisterDto dto, Group group, Teacher teacher) {
        return new Child(
                dto.getEgn(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAge(),
                group,
                dto.getGender().equals("BOY") ? Gender.BOY: Gender.GIRL,
                dto.getParentsEmails(),
                teacher
        );
    }

    public ChildrenProfileDto fromEntityToDto(Child entity, String groupName, String teacherName, List<String> states, List<String> events) {
        return new ChildrenProfileDto(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getId(),
                entity.getAge(),
                groupName,
                entity.getGender().getDescription(),
                teacherName,
                states,
                toReadableFormat(events)
        );
    }

    private List<String> toReadableFormat(List<String> events) {
        List<String> childEvent = new ArrayList<>();
        events.forEach(event -> {
            String name = Arrays.stream(Arrays.stream(event.split(",")).toList().get(1).split("=")).toList().get(1);
            String date = Arrays.stream(Arrays.stream(event.split(",")).toList().get(3).split("=")).toList().get(1);
            String output = name + " " + date;
            childEvent.add(output);
        });
        return childEvent;
    }

    public Event fromDtoToEntity(SelectedDateDto dto) {
        return new Event(
                dto.getName(),
                dto.getDescription(),
                convertDate(dto.getDateTime()),
                currentUsers.findChildrenToTeacher(),
                currentUsers.findCurrentTeacher()
        );
    }

    public Group fromDtoToEntity(GroupDto dto) {
        return new Group(
                dto.getName()
        );
    }

    public Group fromDtoToEntity(Teacher teacher, String groupName) {
        return new Group(
                groupName,
                teacher
        );
    }


    private LocalDate convertDate(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDate.parse(dateTime, formatter);
    }
}
