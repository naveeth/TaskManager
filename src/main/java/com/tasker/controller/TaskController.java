package com.tasker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.*;

/**
 * Created by njanibasha on 9/3/17.
 */
@Api(basePath = "/task", value = "TaskController", description = "Controller to test task")
@RequestMapping(value = {"/task"})
@RestController("TaskController")
public class TaskController {

	@ApiOperation(notes = "Return tasks", value = "", nickname = "",
		tags = {"Tasks"} )
	@ApiResponses({
		  @ApiResponse(code = 200, message = "Nice!", response = String.class),
	})
	@RequestMapping(value = "/test/", method = RequestMethod.GET, produces= "application/json")
	public ResponseEntity<String> getTask() {
		return ResponseEntity.ok().body("test");
	}
	
}
