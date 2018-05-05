package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.dto.ModuleDto;
import com.hfy.logstation.dto.TaskDto;

public interface GraphService {

    ModuleDto taskCount(String index, String system, String module, String task, long start, long end);

    ModuleDto taskCostTime(String index, String system, String module, String task, long start, long end);

    ModuleDto taskSuccessRate(String index, String system, String module, String task, long start, long end);

    TaskDto task(String index, String system, String module, String task, long start, long end);
}
