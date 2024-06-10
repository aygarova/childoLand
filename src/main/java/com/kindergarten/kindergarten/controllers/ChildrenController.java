package com.kindergarten.kindergarten.controllers;

import com.kindergarten.kindergarten.model.dto.ChildRegisterDto;
import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.services.ChildStateService;
import com.kindergarten.kindergarten.services.ChildrenService;
import com.kindergarten.kindergarten.services.GroupService;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/children")
public class ChildrenController {

    private final ChildrenService childrenService;
    private final GroupService groupService;
    private final ChildStateService childStateService;

    public ChildrenController(ChildrenService childrenService, GroupService groupService, ChildStateService childStateService) {
        this.childrenService = childrenService;
        this.groupService = groupService;
        this.childStateService = childStateService;
    }

    @GetMapping("/register")
    public String register(){
        return "child-register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid ChildRegisterDto childRegisterDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("childRegisterDto", childRegisterDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.childRegisterDto"
                            , bindingResult);

            return "redirect:register";
        }

        childrenService.registerChild(childRegisterDto);

        return "redirect:homee"; //TODO
    }


    @PostMapping("/delete")
    public String deleteChild(@RequestParam("id") String id) {
        childrenService.deleteChild(id);
        return "redirect:/admin/groups"; // Пренасочваме към списъка с групи
    }

    @ModelAttribute("childRegisterDto")
    public ChildRegisterDto childRegisterDto() {
        return new ChildRegisterDto();
    }

    @ModelAttribute("child")
    public Child child() {
        return new Child();
    }
}
