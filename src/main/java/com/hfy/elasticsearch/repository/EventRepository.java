package com.hfy.elasticsearch.repository;

import com.hfy.elasticsearch.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by HuangFangyuan on 2018/3/19.
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
}
