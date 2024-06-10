package com.kindergarten.kindergarten.model.entity;

import com.kindergarten.kindergarten.enums.Gender;
import javax.persistence.*;

import java.util.List;


@Entity
@Table(name = "children")
public class Child {

    @Id
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "parents_email", nullable = false)
    private String parentsEmails;

    @OneToMany(mappedBy = "child")
    private List<ChildState> states;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToMany
    @JoinTable(
            name = "child_parent",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "email")
    )
    private List<Parent> parents;

    public Child() {
    }

    public Child(String id, String firstName, String lastName, int age, Group group, Gender gender, String parentsEmails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
        this.gender = gender;
        this.parentsEmails = parentsEmails;
    }

    public Child(String id, String firstName, String lastName, int age, Group group, Gender gender, String parentsEmails, Teacher teacher) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
        this.gender = gender;
        this.parentsEmails = parentsEmails;
        this.teacher = teacher;
    }

    public Child(String id, String firstName, String lastName, int age, Group group, Gender gender, String parentsEmails, List<ChildState> states, Teacher teacher, Event event) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.group = group;
        this.gender = gender;
        this.parentsEmails = parentsEmails;
        this.states = states;
        this.teacher = teacher;
        this.event = event;
        this.parents = parents;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Group getGroup() {
        return group;
    }

    public List<ChildState> getStates() {
        return states;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Gender getGender() {
        return gender;
    }

    public Event getEvent() {
        return event;
    }

    public String getParentsEmails() {
        return parentsEmails;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setStates(List<ChildState> states) {
        this.states = states;
    }
}
