package com.kindergarten.kindergarten.services;

import com.kindergarten.kindergarten.common.convertor.ModelConvertor;
import com.kindergarten.kindergarten.enums.State;
import com.kindergarten.kindergarten.exceptions.ExceptionMessages;
import com.kindergarten.kindergarten.exceptions.WrongActionException;
import com.kindergarten.kindergarten.model.dto.ChildRegisterDto;
import com.kindergarten.kindergarten.model.dto.ChildrenProfileDto;
import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.model.entity.Group;
import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.model.entity.Teacher;
import com.kindergarten.kindergarten.repositories.ChildRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ChildrenService {

    private final ChildRepository childRepository;
    private final GroupService groupService;
    private final ModelConvertor modelConvertor;
    public final ExceptionMessages exceptionMessages;

    public ChildrenService(ChildRepository childRepository, GroupService groupService, ModelConvertor modelConvertor, ExceptionMessages exceptionMessages) {
        this.childRepository = childRepository;
        this.groupService = groupService;
        this.modelConvertor = modelConvertor;
        this.exceptionMessages = exceptionMessages;
    }

    public List<Child> parents (Parent parent){
        List<Child> children = new ArrayList<>();
        childRepository.findAll().forEach(child -> {
            Arrays.stream(parent.getChildEGN().split(",")).toList().forEach(
                    childId -> {
                        if (child.getId().equals(childId)){
                            children.add(child);
                        }
                    }
            );
        });
        if (children.isEmpty()){
            throw new WrongActionException(exceptionMessages.getEGNForOtherChild());
        }
        return children;
    }

    public boolean isParentOfThisChild(String childEGN, String parentEmail){
        Child child = childRepository.findById(childEGN).orElse(null);

        return !Objects.isNull(child) && (checkForSameEmail(parentEmail, Arrays.stream(child.getParentsEmails().split(",")).toList()));
    }

    private boolean checkForSameEmail(String parentEmail, List<String> parentsEmails) {
        return parentsEmails.stream()
                .anyMatch(email -> email.equals(parentEmail));
    }

    public void registerChild(ChildRegisterDto childRegisterDto) {
        Group group = findGroup(childRegisterDto.getGroup());
        Teacher teacher = findTeacher(childRegisterDto.getGroup());
        Child child = modelConvertor.fromDtoToEntity(childRegisterDto, group, teacher);

        if (checkIfExistThisChild(child.getId())){
            throw new WrongActionException(exceptionMessages.getAlreadyExsistUser()); //TODO fix message
        }

        childRepository.save(child);
    }

    public void deleteChild(String id) {
        childRepository.deleteById(id);
    }

    private Teacher findTeacher(String group) {
        return groupService.findGroup(group).getTeacher();
    }

    private String findTeacherName(String group) {
        Teacher teacher = findTeacher(group);
        StringBuilder nameBuilder = new StringBuilder();
        if (teacher.getFirstName() != null && !teacher.getFirstName().isEmpty()) {
            nameBuilder.append(teacher.getFirstName());
        }
        if (teacher.getLastName() != null && !teacher.getLastName().isEmpty()) {
            if (nameBuilder.length() > 0) {
                nameBuilder.append(" ");
            }
            nameBuilder.append(teacher.getLastName());
        }

        return nameBuilder.toString();
    }

    private Group findGroup(String group) {
       return groupService.findGroup(group);
    }

    private boolean checkIfExistThisChild(String egn) {
        return childRepository.findById(egn).isPresent();
    }

    public ChildrenProfileDto findChildWithThisEGN(String id) {
        Child child = findTheChild(id);
        return modelConvertor.fromEntityToDto(child, child.getGroup().getName(), findTeacherName(child.getGroup().getName()), setCorrectState(child), findTheEvents(child));
    }


    private List<String> findTheEvents(Child child) {
        List<String> events = List.of("Няма предстоящи събития");
        return Objects.isNull(child.getEvent()) ? events : List.of(child.getEvent().toString());
    }

    public Child findTheChild(String id) {
        return childRepository.findById(id).orElseThrow(() -> new WrongActionException(exceptionMessages.getAlreadyExsistUser()));
    }

    private List<String> setCorrectState(Child child) {
        List<String> childState = List.of(State.AT_HOME.toString());
        try {
           return child.getStates().isEmpty() ? childState : List.of(child.getStates().toString());
        } catch (NullPointerException e){
            return childState;
        }
    }

    public void saveChild(Child child){
        childRepository.save(child);
    }
}
