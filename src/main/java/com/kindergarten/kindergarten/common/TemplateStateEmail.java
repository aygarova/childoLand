package com.kindergarten.kindergarten.common;

import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.model.entity.ChildState;
import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.model.entity.Teacher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TemplateStateEmail {
    public String title() {
        LocalDate today = LocalDate.now();

        return "Дневни състояния на детето в детската градина - " + today;
    }

    public String description(List<ChildState> statesOfTheDayToTheChild) {
        StringBuilder descriptionBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        DateTimeFormatter formatterTitle = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = statesOfTheDayToTheChild.get(0).getTimestamp().format(formatterTitle);

        Child child = statesOfTheDayToTheChild.get(0).getChild();

        descriptionBuilder.append("Състояние на ")
                .append(child.getFirstName())
                .append(" ")
                .append(child.getLastName())
                .append(" за деня - ").append(date).append(":\n\n");

        for (ChildState childState : statesOfTheDayToTheChild) {
            descriptionBuilder.append(childState.getState().getDescription())
                    .append(" - ")
                    .append(childState.getTimestamp().format(formatter))
                    .append("\n");
        }

        Teacher teacher = statesOfTheDayToTheChild.get(0).getChild().getTeacher();
        descriptionBuilder.append("\n За повече информация може да се свържите с учителя: ")
                .append(teacher.getFirstName())
                .append(" ")
                .append(teacher.getLastName())
                .append("\n")
                .append(" телефонен номер: ")
                .append(teacher.getPhoneNumber())
                .append("\n")
                .append(" имейл: ")
                .append(teacher.getEmail())
                .append("\n");

        return descriptionBuilder.toString();
    }

}
