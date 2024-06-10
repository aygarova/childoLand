package com.kindergarten.kindergarten.controllers;

import com.kindergarten.kindergarten.model.dto.SelectedDateDto;
import com.kindergarten.kindergarten.services.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService calendarService) {
        this.eventService = calendarService;
    }

    @GetMapping("/saveEvent")
    public String register(){
        return "testCalendar";
    }

    @PostMapping("/saveEvent")
    public String saveEvent(@RequestParam("selectedDate") String selectedDate,
                            @RequestParam("selectedTime") String selectedTime,
                            @RequestParam("name") String name,
                            @RequestParam("description") String description) {

        SelectedDateDto selectedDateDto = new SelectedDateDto(name, description,selectedDate + "T"+ selectedTime);
        eventService.saveEvent(selectedDateDto);
        return "redirect:saveEvent";
    }

}
