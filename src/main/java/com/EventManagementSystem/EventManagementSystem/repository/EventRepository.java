package com.EventManagementSystem.EventManagementSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.EventManagementSystem.EventManagementSystem.model.Event;

public interface EventRepository extends JpaRepository<Event,Long> {

}
