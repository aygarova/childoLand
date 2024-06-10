package com.kindergarten.kindergarten.repositories;

import com.kindergarten.kindergarten.model.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, String> {
    Optional<Child> findById(String egn);
}
