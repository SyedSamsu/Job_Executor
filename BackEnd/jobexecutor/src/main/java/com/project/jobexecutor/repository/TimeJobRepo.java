package com.project.jobexecutor.repository;

import com.project.jobexecutor.model.TimeJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeJobRepo extends JpaRepository<TimeJob, Long> {
}
