package com.hfy.logstation.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @Column(unique = true)
    private String account;

    @Column(nullable = false)
    private String password;
}
