package com.kindergarten.kindergarten.model.entity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "descriptions")
    private String description;

    @Column(name = "activate_from", nullable = false, columnDefinition = "DATE")
    private LocalDate localDate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> child;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Event() {
    }

    public Event(long id, String name, String description, LocalDate localDate, List<Child> child, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.localDate = localDate;
        this.child = child;
        this.teacher = teacher;
    }

    public Event(String name, String description, LocalDate localDate, List<Child> child, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.localDate = localDate;
        this.child = child;
        this.teacher = teacher;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public List<Child> getChild() {
        return child;
    }

    public void setChild(List<Child> child) {
        this.child = child;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", localDate=" + localDate +
                ", child=" + child +
                ", teacher=" + teacher +
                '}';
    }
}

