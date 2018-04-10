package com.hfy.logstation.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name= "t_monitor")
public class Monitor {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "m_index", nullable = false)
    private String index;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String field;

    @Column(nullable = false)
    private String value;

    @Column(name = "m_interval")
    private Integer interval;

    @Column(name = "m_interval")
    private Integer frequency;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date createTime;

    private Date modifyTime;

}
