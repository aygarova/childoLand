package com.kindergarten.kindergarten.model.entity;

import com.kindergarten.kindergarten.enums.State;
import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChildState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Enumerated(EnumType.STRING)
    private State state;

    private LocalDateTime timestamp;

    public ChildState() {
    }

    public ChildState(Long id, Child child, State state, LocalDateTime timestamp) {
        this.id = id;
        this.child = child;
        this.state = state;
        this.timestamp = timestamp;
    }

    public ChildState(State state, LocalDateTime atStartOfDay) {
        this.state = state;
        this.timestamp = atStartOfDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
