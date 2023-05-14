package com.project.jobexecutor.controller;

import com.project.jobexecutor.model.User;
import com.project.jobexecutor.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobexecutor/v1")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @PostMapping(value = "/signup")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> signUpUser(@RequestBody User user) {
        userRepo.save(user);
        return new ResponseEntity<>("Registered Successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User userData = userRepo.findByName(user.getName());
        if(userData.getPassword().equals(user.getPassword()))
            return new ResponseEntity<>("Login Successfully", HttpStatus.OK);

        return new ResponseEntity<>("Login Failed", HttpStatus.OK);
    }

}
