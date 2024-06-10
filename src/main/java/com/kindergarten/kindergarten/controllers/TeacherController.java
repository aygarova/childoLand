package com.kindergarten.kindergarten.controllers;

import com.kindergarten.kindergarten.model.dto.UserLoginDto;
import com.kindergarten.kindergarten.model.dto.TeacherRegistrationDto;
import com.kindergarten.kindergarten.model.entity.Group;
import com.kindergarten.kindergarten.model.entity.Teacher;
import com.kindergarten.kindergarten.services.ChildStateService;
import com.kindergarten.kindergarten.services.EventService;
import com.kindergarten.kindergarten.services.GroupService;
import com.kindergarten.kindergarten.services.TeacherService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final GroupService groupService;
    private final ChildStateService childState;
    private final EventService eventService;

    public TeacherController(TeacherService teacherService, GroupService groupService, ChildStateService childState, EventService eventService) {
        this.teacherService = teacherService;
        this.groupService = groupService;
        this.childState = childState;
        this.eventService = eventService;
    }

    @GetMapping("/group")
    public String getGroupDetails(Model model, Principal principal) {
        String teacherEmail = "poli.plinova@gmail.com"; //
        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        Group group = groupService.getGroupByTeacherEmail(teacher.getEmail());
        model.addAttribute("group", group);
        return "teacher-children";
    }

    @PostMapping("/children/changeState")
    public String changeChildState(@RequestParam("childId") String childId, @RequestParam("state") String state) {
        childState.changeChildState(childId, state);
        return "redirect:/teacher/group"; // Заменете с действителния URL на списъка с деца
    }

    @PostMapping("/sendReport")
    public String changeChildState(@RequestParam("childId") String childId) {
        eventService.sendEmailOnTheEndOfTheDay(childState.findStatesOfTheDayToTheChild(childId), childId);
        return "redirect:/teacher/group"; // Заменете с действителния URL на списъка с деца
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();

        return "redirect:/";
    }

    @ModelAttribute("teacherRegistrationDto")
    public TeacherRegistrationDto teacherRegistrationDto() {
        return new TeacherRegistrationDto();
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto teacherLoginDto() {
        return new UserLoginDto();
    }

}
