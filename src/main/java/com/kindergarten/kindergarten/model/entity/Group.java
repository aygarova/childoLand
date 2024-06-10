package com.kindergarten.kindergarten.model.entity;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<Child> child;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public Group(Long id, String name, Set<Child> child, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.child = child;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Child> getChild() {
        List<Child> sortedChildren = new ArrayList<>(this.child);
        Collections.sort(sortedChildren, Comparator.comparing(Child::getFirstName).thenComparing(Child::getLastName));
        return new LinkedHashSet<>(sortedChildren);
    }

    public void setChild(Set<Child> children) {
        this.child = children;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
