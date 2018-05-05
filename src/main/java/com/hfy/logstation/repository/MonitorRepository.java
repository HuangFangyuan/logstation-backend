package com.hfy.logstation.repository;

import com.hfy.logstation.entity.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor, Integer> {
    List<Monitor> findByActive(boolean isActive);
}
