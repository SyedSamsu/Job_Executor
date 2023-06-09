package com.project.jobexecutor.repository;

import com.project.jobexecutor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByName(String name);

}
