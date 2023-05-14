package com.project.jobexecutor.repository;

import com.project.jobexecutor.model.EventJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventJobRepo extends JpaRepository<EventJob, Long> {

    Optional<EventJob> findByJobEvent(String name);

}
