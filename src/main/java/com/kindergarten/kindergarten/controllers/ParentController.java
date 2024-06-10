package com.kindergarten.kindergarten.controllers;

import com.kindergarten.kindergarten.enums.State;
import com.kindergarten.kindergarten.model.dto.ChildrenProfileDto;
import com.kindergarten.kindergarten.model.dto.ParentRegistrationDto;
import com.kindergarten.kindergarten.model.dto.SelectedDateDto;
import com.kindergarten.kindergarten.model.dto.UserLoginDto;
import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.model.entity.ChildState;
import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.services.ChildStateService;
import com.kindergarten.kindergarten.services.ChildrenService;
import com.kindergarten.kindergarten.services.ParentService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/parent")
public class ParentController {

    private final ParentService parentService;
    private final ChildrenService childrenService;
    private final ChildStateService childStateService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm");


    public ParentController(ParentService parentService, ChildrenService childrenService, ChildStateService childStateService) {
        this.parentService = parentService;
        this.childrenService = childrenService;
        this.childStateService = childStateService;
    }

    @GetMapping("/children")
    public String getParentChildren(Model model, Principal principal) {
        String parentEmail = "gabriela_aygarova@abv.bg"; // TODO principal.getName()
        Parent parent = parentService.findParentByEmail(parentEmail);
        List<Child> children = parentService.getChildrenByParentEmail(parentEmail);
        Boolean isActive = parent.isActive(); // вземете isActive от базата данни или от другото място, където се съхраняват настройките

        // Извличаме децата на родителя от базата данни
        model.addAttribute("children", children); // Предаваме децата на изгледаreturn "parent-all-child"; // Името на изгледа, който ще се покаже
        model.addAttribute("parent", parent); // Предаваме децата на изгледаreturn "parent-all-child"; // Името на изгледа, който ще се покаже
        model.addAttribute("isActive", isActive);
        return "parent-all-child";
    }

    @PostMapping("/children/add")
    public String addChildById(@RequestParam("childId") String childId, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            String parentEmail = "gabriela_aygarova@abv.bg";
            if (parentService.saveNewChild(childId, parentEmail)) {
                redirectAttributes.addFlashAttribute("successMessage", "Детето беше успешно добавено.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Не беше намерено дете с това ID.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Възникна грешка при добавянето на детето.");
        }
        return "redirect:/parent/children";
    }

    @PostMapping("/children/update-email-preferences")
    public String updateEmailPreferences(@RequestParam(value = "isActive", required = false) Boolean isActive, Principal principal) {
        String parentEmail = "gabriela_aygarova@abv.bg";
        if (isActive == null) {
            isActive = false; // Задаване на false, ако isActive е null
        }
        parentService.updateEmailPreferences(parentEmail, isActive);
        return "redirect:/parent/children";
    }

    @GetMapping("/children/profile")
    public String getChildrenProfile(@RequestParam("childId") String id,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     Model model) {
        ChildrenProfileDto children = childrenService.findChildWithThisEGN(id);
        List<ChildState> states = childStateService.getChildStatesForLastMonth(id);
        Map<LocalDate, List<ChildState>> statesByDay = states.stream()
                .collect(Collectors.groupingBy(state -> state.getTimestamp().toLocalDate()));

        model.addAttribute("children", children);
        model.addAttribute("statesByDay", statesByDay);

        if (date == null) {
            date = LocalDate.now();
        }

        List<ChildState> statesForSelectedDate = statesByDay.getOrDefault(date, List.of());
        model.addAttribute("selectedDate", date);
        model.addAttribute("statesForSelectedDate", statesForSelectedDate);

        return "child-profile";
    }

    @ModelAttribute("parentRegistrationDto")
    public ParentRegistrationDto parentRegistrationDto() {
        return new ParentRegistrationDto();
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto teacherLoginDto() {
        return new UserLoginDto();
    }
}
