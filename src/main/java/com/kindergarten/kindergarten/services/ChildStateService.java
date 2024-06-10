package com.kindergarten.kindergarten.services;

import com.kindergarten.kindergarten.enums.State;
import com.kindergarten.kindergarten.model.entity.Child;
import com.kindergarten.kindergarten.model.entity.ChildState;
import com.kindergarten.kindergarten.repositories.ChildStateRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChildStateService {
    private final ChildStateRepository childStateRepository;
    private final ChildrenService childrenService;

    public ChildStateService(ChildStateRepository childStateRepository, ChildrenService childrenService) {
        this.childStateRepository = childStateRepository;
        this.childrenService = childrenService;
    }

    public void saveChildState(Long childId, State state) {
        ChildState childState = new ChildState();
        childState.setId(childId);
        childState.setState(state);
        childState.setTimestamp(LocalDateTime.now());
        childStateRepository.save(childState);
    }

    public List<ChildState> getChildStatesForLastMonth(String childId) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        return childStateRepository.findByChildIdAndTimestampAfter(childId, oneMonthAgo);
    }

//    public List<ChildState> getChildStatesForThisDate(LocalDate) {
//        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
//        return childStateRepository.findByChildIdAndTimestampAfter(childId, oneMonthAgo);
//    }

    public void changeChildStatea(String childId, String state) {
        Child child = childrenService.findTheChild(childId);
        LocalDate today = LocalDate.now();

        // Вземаме всички състояния за днешния ден
        List<ChildState> statesToday = new ArrayList<>();
        childStateRepository.findAll().forEach(childState -> {
            if (childState.getChild().getId().equals(childId) && childState.getTimestamp().toLocalDate().equals(today)){
                statesToday.add(childState);
            }
        });

        ChildState childState = new ChildState();

        // Ако няма състояния за днешния ден, създаваме ново състояние
        if (statesToday.isEmpty()) {
            childState.setChild(child);
            childState.setState(State.valueOf(state));
            childState.setTimestamp(LocalDateTime.now());
            childStateRepository.save(childState);
            child.getStates().add(childState);
        } else {
            // Ако има състояния за днешния ден, актуализираме съществуващите състояния
                childState.setChild(child);
                childState.setState(State.valueOf(state));
                childState.setTimestamp(LocalDateTime.now());
                statesToday.add(childState);
                childStateRepository.save(childState);
                child.getStates().add(childState);

        }

        childrenService.saveChild(child);
    }
    public void changeChildState(String childId, String state) {
        Child child = childrenService.findTheChild(childId);

        ChildState childState = new ChildState();
        childState.setChild(child);
        childState.setState(State.valueOf(state));
        childState.setTimestamp(LocalDateTime.now());
        childStateRepository.save(childState);
    }

    public List<ChildState> findStatesOfTheDayToTheChild(String childId) {
        return childStateRepository.findByChildIdAndTimestampAfter(childId, LocalDate.now().atStartOfDay());
    }

}
