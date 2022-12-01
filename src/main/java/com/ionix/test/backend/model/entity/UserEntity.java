package com.ionix.test.backend.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="tasks")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="userName")
    private String userName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

}
