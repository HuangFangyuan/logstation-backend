package com.hfy.logstation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
@Entity
@Data
@Table(name = "t_contact")
public class Contact {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String value;

    @Column(name = "default_use", nullable = false)
    private Boolean defaultUse;
}
