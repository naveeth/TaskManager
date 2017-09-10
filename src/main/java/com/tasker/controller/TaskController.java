package com.tasker.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tasker.model.Task;
import com.tasker.services.TaskService;
import com.tasker.services.TaskValidationService;

/**
 * Created by njanibasha on 9/3/17.
 */
@Api(basePath = "/task", value = "TaskController", description = "Controller to test task")
@RequestMapping(value = { "/task" })
@RestController("TaskController")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskValidationService taskValidationService;

    @ApiOperation(notes = "Add a task", value = "", nickname = "",
            tags = { "Task" })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nice!", response = String.class),
    })
    @RequestMapping(value = "/add/", method = RequestMethod.POST)
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        String result = "Sorry, We could not add your task.";
        StringBuilder validationResult = taskValidationService.verifyTaskDetail(task);
        if (validationResult.length() == 0 && taskService.addTask(task)) {
            result = "Task has been added successfully!!";
        }
        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(notes = "Add list of tasks", value = "", nickname = "",
            tags = { "Task" })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nice!", response = String.class),
    })
    @RequestMapping(value = "/addTasks/", method = RequestMethod.POST)
    public ResponseEntity<String> addTasks(@RequestBody List<Task> tasks) {
        String result = "Sorry, We could not add your task.";
        if (taskValidationService.verifyTaskDetails(tasks) && taskService.addTasks(tasks)) {
            result = "Tasks have been added successfully!!";
        }
        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(notes = "List of tasks", value = "", nickname = "",
            tags = { "Tasks" })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Got List of Tasks successfully!", response = List.class),
    })
    @RequestMapping(value = "/tasks/", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> taskList = taskService.getTasks();
        return ResponseEntity.ok().body(taskList);
    }

}
