package com.kindergarten.kindergarten.controllers;

import com.kindergarten.kindergarten.model.dto.ChildRegisterDto;
import com.kindergarten.kindergarten.model.dto.GroupDto;
import com.kindergarten.kindergarten.model.dto.UserLoginDto;
import com.kindergarten.kindergarten.model.entity.Group;
import com.kindergarten.kindergarten.model.entity.Teacher;
import com.kindergarten.kindergarten.services.ChildrenService;
import com.kindergarten.kindergarten.services.GroupService;
import com.kindergarten.kindergarten.services.TeacherService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ChildrenService childrenService;
    private final TeacherService teacherService;
    private final GroupService groupService;

    public AdminController(ChildrenService childrenService, TeacherService teacherService, GroupService groupService) {
        this.childrenService = childrenService;
        this.teacherService = teacherService;
        this.groupService = groupService;
    }

    @GetMapping("/start")
    public String start(){
        return "start-page";
    }


    @GetMapping()
    public String groups(){
        return "admin";
    }

    @GetMapping("/groups")
    public String groups(Model model){
        if (groupService.getAllGroups().isEmpty()){
            return "add-group";
        }
        model.addAttribute("groups", groupService.getAllGroups());

        return "groups";
    }


    @GetMapping("/group/{groupName}")
    public String group(@PathVariable String groupName, Model model){
        model.addAttribute("groups", groupService.getGroup(groupName));
        return "selectedGroup";
    }

    @GetMapping("/groups-add")
    public String addGroup(Model model){
        GroupDto groupDto = new GroupDto();
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("groupDto", groupDto);
        model.addAttribute("teachers", teachers);
        return "add-group";
    }


    @PostMapping("/groups-add")
    public String addGroup(@Valid GroupDto groupDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("groupDto", groupDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.groupDto"
                            , bindingResult);

            return "add-group";
        }

        groupService.addGroup(groupDto);
        return "admin";
    }



    @GetMapping("/addChild")
    public String addChild(Model model){
        model.addAttribute("childRegisterDto", new ChildRegisterDto());
        model.addAttribute("groups", groupService.getAllGroups());
        return "child-register";
    }


    @PostMapping("/addChild")
    public String addChild(@Valid ChildRegisterDto childRegisterDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("childRegisterDto", childRegisterDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.childRegisterDto"
                            , bindingResult);

            return "child-register";
        }

        childrenService.registerChild(childRegisterDto);
        return "admin";
    }

    @GetMapping("/all-groups")
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "groups";
    }

    @GetMapping("/group")
    public String getGroupDetails(@RequestParam("name") String groupName, Model model) {
        Group group = groupService.getGroupByName(groupName);
        model.addAttribute("group", group);
        return "group-details";
    }


    @ModelAttribute
    public GroupDto groupDto(){
        return new GroupDto();
    }

    @ModelAttribute
    public ChildRegisterDto childRegisterDto(){
        return new ChildRegisterDto();
    }

}
