package com.tasker.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tasker.dao.TaskDao;
import com.tasker.model.Task;

/**
 * Created by njanibasha on 9/7/17.
 */
@Service
@EnableAsync
@EnableScheduling
public class TaskService {

    @Autowired
    TaskDao taskDao;

    public boolean addTask(Task task) {
        boolean result = false;
        if (task != null) {
            result = taskDao.add(task);
        }
        return result;
    }

    public List<Task> getTasks() {
        return taskDao.getTasks();
    }

    public boolean addTasks(List<Task> taskList) {
        boolean result = false;
        try {
            if (taskList != null) {
                System.out.println("No. of tasks to be added - " + taskList.size());
                result = taskDao.addTasks(taskList);
                taskDao.getTasks();
            }
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Scheduled(initialDelay = 100000, fixedRate = 300000)
    public void consumeTasksFromQueue() {
        taskDao.consumeTasks();
    }

}
