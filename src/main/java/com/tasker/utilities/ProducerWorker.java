package com.tasker.utilities;

import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;

import com.tasker.model.Task;

/**
 * Created by njanibasha on 9/7/17.
 */
public class ProducerWorker implements Callable<StringBuilder> {

    private PriorityBlockingQueue<Task> priorityBlockingQueue;
    private Task task;

    public ProducerWorker(PriorityBlockingQueue<Task> priorityBlockingQueue, Task task) {
        this.priorityBlockingQueue = priorityBlockingQueue;
        this.task = task;
    }

    public StringBuilder call() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Task ( %s ) has been added successfully - %s", task, priorityBlockingQueue.offer(task)));
        return stringBuilder;
    }

}
