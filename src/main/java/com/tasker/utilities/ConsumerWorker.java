package com.tasker.utilities;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tasker.model.Task;

/**
 * Created by njanibasha on 9/7/17.
 */
public class ConsumerWorker implements Callable<StringBuilder> {

    private PriorityBlockingQueue<Task> taskPriorityBlockingQueue;
    private PriorityBlockingQueue<Task> completedQueue;

    public ConsumerWorker(PriorityBlockingQueue<Task> priorityBlockingQueue, PriorityBlockingQueue<Task> completedQueue) {
        this.taskPriorityBlockingQueue = priorityBlockingQueue;
        this.completedQueue = completedQueue;
    }

    public StringBuilder call() throws Exception {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (taskPriorityBlockingQueue.size() == 0) {
                return sb.append("Queue is Now Empty!");
            }
            else {
                Task topNode = taskPriorityBlockingQueue.peek();
                if (topNode != null) {
                    long diff =
                            new Date().getTime() - topNode.getReminderDate()
                                    .getTime();
                    long diffMins = diff / (60 * 1000) % 60;
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                    if (minutes <= 15) {
                        Task polledOutTask = taskPriorityBlockingQueue.take();
                        completedQueue.add(polledOutTask);
                        System.out.println("--> " + polledOutTask);
                        sb.append(" polled task " + polledOutTask.getTitle() + " - "
                                + polledOutTask.getDescription() + " \n");
                    }
                    else if (diff > 0) {
                        Task polledOutTask = taskPriorityBlockingQueue.take();
                        System.out.println("----> " + polledOutTask);
                        completedQueue.add(polledOutTask);
                    }
                    else {
                        return sb.append("Check later");
                    }
                }
            }
        }
    }
}
