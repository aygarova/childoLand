package com.kindergarten.kindergarten.services;

import com.kindergarten.kindergarten.common.convertor.ModelConvertor;
import com.kindergarten.kindergarten.model.dto.GroupDto;
import com.kindergarten.kindergarten.model.entity.Group;
import com.kindergarten.kindergarten.model.entity.Teacher;
import com.kindergarten.kindergarten.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final TeacherService teacherService;
    private final ModelConvertor modelConvertor;


    public GroupService(GroupRepository groupRepository, TeacherService teacherService, ModelConvertor modelConvertor) {
        this.groupRepository = groupRepository;
        this.teacherService = teacherService;
        this.modelConvertor = modelConvertor;
    }

    public void saveGroup(GroupDto groupDto) {
        Group group = modelConvertor.fromDtoToEntity(groupDto);
        groupRepository.save(group);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void addGroup(GroupDto groupDto) {
        if (getAllGroups().isEmpty()){
            Group group = modelConvertor.fromDtoToEntity(teacherService.findTeacher(groupDto), groupDto.getName());
            groupRepository.save(group);
            teacherService.saveGroup(group);
        }
        if (!haveThisGroup(groupDto.getName())){
            Group group = modelConvertor.fromDtoToEntity(teacherService.findTeacher(groupDto), groupDto.getName());
            groupRepository.save(group);
            teacherService.saveGroup(group);        }
       // throw new WrongActionException(ExceptionMessages.CATEGORY_ALREADY_EXIST_EXCEPTIONS); TODO
    }

    public boolean haveThisGroup(String name){
        List<Group> groups = groupRepository.getGroupByName(name);
        for (Group group:groups) {
            if (group.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public List<Group> getGroup(String groupName) {
        return groupRepository.getGroupByName(groupName);
    }

    public Group getGroupByTeacherEmail(String teacherEmail){
        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        return teacher.getGroup();
    }

    public Group getGroupByName(String groupName){
        return groupRepository.getGroupByName(groupName).get(0);
    }
    public Group findGroup(String group) {
        return groupRepository.getGroupByName(group).get(0);
    }
}
