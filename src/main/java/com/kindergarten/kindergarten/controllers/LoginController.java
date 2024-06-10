package com.kindergarten.kindergarten.controllers;

import com.kindergarten.kindergarten.model.dto.ParentRegistrationDto;
import com.kindergarten.kindergarten.model.dto.TeacherRegistrationDto;
import com.kindergarten.kindergarten.model.dto.UserLoginDto;
import com.kindergarten.kindergarten.services.ParentService;
import com.kindergarten.kindergarten.services.TeacherService;
import com.kindergarten.kindergarten.services.UserLoginService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserLoginService userLoginService;
    private final TeacherService teacherService;
    private final ParentService parentService;

    public LoginController(UserLoginService userLoginService, TeacherService teacherService, ParentService parentService) {
        this.userLoginService = userLoginService;
        this.teacherService = teacherService;
        this.parentService = parentService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        return "home";
    }

    @GetMapping("/start")
    public String showStartPages(Model model) {
        return "start-page";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("userLoginDto", new UserLoginDto());
        return "login"; // Това е името на логин изгледа
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        return "register"; // Това е името на логин изгледа
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginDto userLoginDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userLoginDto", userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDto", bindingResult);

            return "redirect:/login";
        }

        if (!userLoginService.loginUser(userLoginDto)){
            redirectAttributes.addFlashAttribute("userLoginDto", userLoginDto);
            redirectAttributes.addFlashAttribute("isFound", false);
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/teacher-register")
    public String register(){
        return "teacher-register";
    }

    @PostMapping("/teacher-register")
    public String registerConfirm(@Valid TeacherRegistrationDto teacherRegistrationDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !teacherRegistrationDto.getPassword()
                .equals(teacherRegistrationDto.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute("teacherRegistrationDto", teacherRegistrationDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.teacherRegistrationDto"
                            , bindingResult);

            return "redirect:register";
        }

        teacherService.registerUser(teacherRegistrationDto);


        return "redirect:login";
    }

    @GetMapping("parent-register")
    public String registerParent(){
        return "parent-register";
    }

    @PostMapping("/parent-register")
    public String registerConfirm(@Valid ParentRegistrationDto parentRegistrationDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !parentRegistrationDto.getPassword()
                .equals(parentRegistrationDto.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute("parentRegistrationDto", parentRegistrationDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.parentRegistrationDto"
                            , bindingResult);

            return "redirect:register";
        }

        parentService.registerUser(parentRegistrationDto);


        return "redirect:login";
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto userLoginDto() {
        return new UserLoginDto();
    }

    @ModelAttribute("teacherRegistrationDto")
    public TeacherRegistrationDto teacherRegistrationDto() {
        return new TeacherRegistrationDto();
    }

    @ModelAttribute("parentRegistrationDto")
    public ParentRegistrationDto parentRegistrationDto() {
        return new ParentRegistrationDto();
    }

}
