package com.tasker.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tasker.model.Task;

/**
 * Created by njanibasha on 9/9/17.
 */
@Service
public class TaskValidationService {

    public boolean verifyTaskDetails(List<Task> tasks) {
        boolean result = false;
        StringBuilder tasksValidationBuilder = new StringBuilder();
        if (tasks == null) {
            tasksValidationBuilder.append("List of Tasks is null. Please, provide proper input.");
        }
        for (Task task : tasks) {
            tasksValidationBuilder.append(verifyTaskDetail(task));
        }
        if (tasksValidationBuilder.length() == 0) {
            result = true;
        }
        return result;
    }

    public StringBuilder verifyTaskDetail(Task task) {
        StringBuilder validationResultBuilder = new StringBuilder();
        if (StringUtils.isEmpty(task.getTitle())) {
            validationResultBuilder.append("Task title is empty");
        }
        if (StringUtils.isEmpty(task.getDescription())) {
            validationResultBuilder.append("Task description is empty");
        }
        if (new Date().after(task.getReminderDate())) {
            validationResultBuilder.append("Task reminder date is not a future date");
        }
        return validationResultBuilder;
    }

}
