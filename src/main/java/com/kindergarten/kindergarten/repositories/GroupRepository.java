package com.kindergarten.kindergarten.repositories;

import com.kindergarten.kindergarten.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> getGroupByName(String name);
}
