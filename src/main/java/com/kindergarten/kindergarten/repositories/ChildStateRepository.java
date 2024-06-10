package com.kindergarten.kindergarten.repositories;

import com.kindergarten.kindergarten.model.entity.ChildState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ChildStateRepository extends JpaRepository<ChildState, String> {
    List<ChildState> findByChildIdAndTimestampAfter(String  childId, LocalDateTime timestamp);

}
