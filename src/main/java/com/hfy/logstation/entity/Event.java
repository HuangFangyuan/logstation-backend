package com.hfy.logstation.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by HuangFangyuan on 2018/3/19.
 */
@Entity
@Data
@Table(name= "t_event")
public class Event {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Date createTime;

    @ManyToOne(targetEntity = Monitor.class)
    @JoinColumn(name = "m_id")
    private Monitor monitor;

    @Column(nullable = false)
    private boolean send;
}
