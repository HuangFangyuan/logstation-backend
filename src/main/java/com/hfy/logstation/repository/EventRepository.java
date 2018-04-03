package com.hfy.logstation.repository;

import com.hfy.logstation.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by HuangFangyuan on 2018/3/19.
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
}
