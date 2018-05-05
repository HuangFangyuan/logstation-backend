package com.hfy.logstation.controller;

import com.hfy.logstation.entity.Response;
import com.hfy.logstation.service.interfaces.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.hfy.logstation.util.ResponseUtil.success;

@RestController
@RequestMapping("graph")
public class GraphController {

    private GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping(value = "/module/count")
    public Response<Object> executionCount(@RequestParam("index") String index,
                                  @RequestParam("start") long startTime,
                                  @RequestParam("end") long endTime,
                                  @RequestParam("system") String system,
                                  @RequestParam("module") String module) {

        return success(graphService.taskCount(index, system, module, null, startTime, endTime));
    }

    @GetMapping(value = "/module/cost")
    public Response<Object> timeCost(@RequestParam("index") String index,
                                 @RequestParam("start") long startTime,
                                 @RequestParam("end") long endTime,
                                 @RequestParam("system") String system,
                                 @RequestParam("module") String module) {

        return success(graphService.taskCostTime(index, system, module, null, startTime, endTime));
    }

    @GetMapping(value = "/module/rate")
    public Response<Object> successRate(@RequestParam("index") String index,
                                 @RequestParam("start") long startTime,
                                 @RequestParam("end") long endTime,
                                 @RequestParam("system") String system,
                                 @RequestParam("module") String module) {

        return success(graphService.taskSuccessRate(index, system, module, null, startTime, endTime));
    }

    @GetMapping(value = "task")
    public Response<Object> taskGraph(@RequestParam("index") String index,
                                 @RequestParam("start") long startTime,
                                 @RequestParam("end") long endTime,
                                 @RequestParam("system") String system,
                                 @RequestParam("module") String module,
                                 @RequestParam("task") String task) {

        return success(graphService.task(index, system, module, task, startTime, endTime));
    }
}
