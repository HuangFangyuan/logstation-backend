package com.hfy.logstation.repository;

import com.hfy.logstation.entity.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
public interface MonitorRepository extends JpaRepository<Monitor, Integer> {
    List<Monitor> findByActive(boolean isActive);
    Monitor findById(int id);
}
