package com.EventManagementSystem.EventManagementSystem.repository;

import com.EventManagementSystem.EventManagementSystem.model.IdentifyUser;
import com.EventManagementSystem.EventManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentifyRepository extends JpaRepository<IdentifyUser,Long> {
    Optional<Object> findByUser(User user);
}
