package com.hfy.logstation.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name= "t_event")
public class Event {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Date createTime = new Date();

    @ManyToOne(targetEntity = Monitor.class)
    @JoinColumn(name = "m_id")
    private Monitor monitor;

    @Column(nullable = false)
    private boolean send = false;
}
