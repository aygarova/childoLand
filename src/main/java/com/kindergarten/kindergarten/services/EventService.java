package com.kindergarten.kindergarten.services;

import com.kindergarten.kindergarten.common.EmailSender;
import com.kindergarten.kindergarten.common.TemplateStateEmail;
import com.kindergarten.kindergarten.common.convertor.ModelConvertor;
import com.kindergarten.kindergarten.model.dto.SelectedDateDto;
import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.model.entity.ChildState;
import com.kindergarten.kindergarten.model.entity.Event;
import com.kindergarten.kindergarten.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EmailSender sendEmail;
    private final ChildrenService childrenService;
    private final ParentService parentService;
    private final TemplateStateEmail templateStateEmail;
    private final ModelConvertor modelConvertor;

    public EventService(EventRepository eventRepository, EmailSender emailSender, ChildrenService childrenService, ParentService parentService, TemplateStateEmail templateStateEmail, ModelConvertor modelConvertor) {
        this.eventRepository = eventRepository;
        this.sendEmail = emailSender;
        this.childrenService = childrenService;
        this.parentService = parentService;
        this.templateStateEmail = templateStateEmail;
        this.modelConvertor = modelConvertor;
    }

    public void saveEvent(SelectedDateDto dateRequest) {
        Event event = modelConvertor.fromDtoToEntity(dateRequest);
        for (Child child : event.getChild()) {
            child.setEvent(event);
        }

        List<Child> children = new ArrayList<>();
        for (Child childDto : event.getChild()) {
            Child child = childDto;
            // Прехвърляне на полетата от DTO към Child Entity
            // child.setSomeField(childDto.getSomeField());
            child.setEvent(event); // Установяване на връзката с текущото събитие
            children.add(child);
        }
        event.setChild(children); // Установяване на колекцията от деца за текущото събитие

        eventRepository.save(event);
        sentToParentEmail(event);
    }

    public void sendEmailOnTheEndOfTheDay(List<ChildState> state, String childId) {
        Child child = childrenService.findTheChild(childId);
        List<String> parentEmail = checkAllParentEmailAreActive(child);
        if (parentEmail.isEmpty()){
            return;
            //TODO throw exception no parent to which sent this email
        }
        sentToEmail(parentEmail, state);
    }

    private void sentToEmail(List<String> emails, List<ChildState> state) {
        sendEmail.sentEmail(emails, templateStateEmail.title(), templateStateEmail.description(state));
    }

    private void sentToParentEmail(Event event) {
        List<Child> childrenForWhichIsThisEvent = event.getChild();
        List<String> parentsEmails = new ArrayList<>();
        childrenForWhichIsThisEvent.forEach(child -> {
            parentsEmails.addAll(checkAllParentEmailAreActive(child));
        });
        if (parentsEmails.isEmpty()){
            return;
            //TODO throw exception no parent to which sent this email
        }
        sendEmail.sentEmail(parentsEmails, event.getName(), event.getDescription());
    }

    private List<String> checkAllParentEmailAreActive(Child child) {
        List<String> activeParentEmail = new ArrayList<>();
        Arrays.stream(child.getParentsEmails().split(",")).toList().stream().forEach(email -> {
            if (checkIfParentEmailIsActive(email)){
                activeParentEmail.add(email);
            }
        });
        return activeParentEmail;
    }

    private boolean checkIfParentEmailIsActive(String recipientEmail) {
        return parentService.findParentByEmail(recipientEmail).isActive();
    }
}

