package com.tasker.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.tasker.model.Task;
import com.tasker.utilities.ConsumerWorker;
import com.tasker.utilities.ProducerWorker;

/**
 * Created by njanibasha on 9/4/17.
 */
@Component
public class TaskDao {
    // Use inMemory data structure
    PriorityBlockingQueue<Task> taskPriorityQueueInMemory = new PriorityBlockingQueue<Task>();

    PriorityBlockingQueue<Task> completedQueue = new PriorityBlockingQueue<Task>();

    @Autowired
    @Qualifier("producerTaskExecutor")
    ThreadPoolTaskExecutor producerExecutor;

    @Autowired
    @Qualifier("consumerTaskExecutor")
    ThreadPoolTaskExecutor consumerExecutor;

    public boolean add(Task task) {
        boolean result = false;
        if (task != null) {
            Future<StringBuilder> taskFuture = producerExecutor.submit(new ProducerWorker(taskPriorityQueueInMemory, task));
            StringBuilder resultBuilder = new StringBuilder();
            while (true) {
                if (taskFuture.isDone() || taskFuture.isCancelled()) {
                    try {
                        resultBuilder.append(taskFuture.get()
                                .toString());
                    }
                    catch (InterruptedException e) {
                        resultBuilder.append(e.getMessage());
                        e.printStackTrace();
                    }
                    catch (ExecutionException e) {
                        resultBuilder.append(e.getMessage());
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        }
        else {
            return result;
        }
    }

    public List<Task> getTasks() {
        Task[] taskArray = taskPriorityQueueInMemory.toArray(new Task[taskPriorityQueueInMemory.size()]);
        Arrays.sort(taskArray);
        return Arrays.asList(taskArray);
    }

    public StringBuilder consumeTasks() {
        List<Future<StringBuilder>> taskResult = new ArrayList<Future<StringBuilder>>();
        StringBuilder output = new StringBuilder();
        System.out.println("Poll the task from priority queue");
        try {
            Future<StringBuilder> result = consumerExecutor.submit(new ConsumerWorker(taskPriorityQueueInMemory, completedQueue));
            while (!result.isDone()) {
                System.out.println("Processing the queue");
                Thread.sleep(300);
            }
            output = result.get();
            System.out.println("-- Consumed task -- " + output.toString());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        return output;
    }

    public boolean addTasks(List<Task> taskList) throws ExecutionException, InterruptedException {
        boolean result = true;
        if (taskList != null) {
            List<Future<StringBuilder>> tasksResult = new ArrayList<Future<StringBuilder>>(taskList.size());
            for (Task task : taskList) {
                Future<StringBuilder> taskFuture =
                        producerExecutor.submit(new ProducerWorker(taskPriorityQueueInMemory, task));
                tasksResult.add(taskFuture);
            }
            StringBuilder resultBuilder = new StringBuilder();
            // will wait until all the task addition process completes
            while (true) {
                int i = 0;
                for (Future<StringBuilder> taskResult : tasksResult) {
                    boolean localResult = taskResult.isCancelled() || taskResult.isDone();
                    resultBuilder.append(taskResult.get()
                            .toString());
                    i++;
                }
                if (i == tasksResult.size()) {
                    break;
                }
            }
        }
        else {
            return false;
        }
        return result;
    }
}
