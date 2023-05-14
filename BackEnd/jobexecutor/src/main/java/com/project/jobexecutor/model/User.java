package com.project.jobexecutor.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USER_TABLE")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name= "ROLE")
    private String role;

}
