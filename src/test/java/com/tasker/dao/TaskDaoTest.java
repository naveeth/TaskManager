package com.tasker.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.tasker.model.Task;
import com.tasker.utilities.ConsumerWorker;
import com.tasker.utilities.ProducerWorker;

/**
 * Created by njanibasha on 9/9/17.
 */
public class TaskDaoTest {

    @InjectMocks
    TaskDao taskDaoMockTest;

    @Mock
    ThreadPoolTaskExecutor producerExecutor;

    @Mock
    ThreadPoolTaskExecutor consumerExecutor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTasks() throws ExecutionException, InterruptedException {
        List<Task> mockTaskList = new ArrayList<Task>();
        for (int i = 0; i < 4; i++) {
            Date currentTime = new Date();
            mockTaskList.add(new Task("Task " + i, " task to verify mock test ", currentTime, currentTime, currentTime, "NEW"));
        }
        when(producerExecutor.submit(any(ProducerWorker.class))).thenAnswer(new Answer<Future<StringBuilder>>() {
            public Future<StringBuilder> answer(InvocationOnMock invocation) throws Throwable {
                Future<StringBuilder> future = mock(FutureTask.class);
                when(future.isDone()).thenReturn(false, false, true);
                when(future.get()).thenReturn(new StringBuilder("This is a test"));
                return future;
            }
        });
        boolean result = taskDaoMockTest.addTasks(mockTaskList);
        assertEquals(result, Boolean.TRUE);
        verify(producerExecutor, atLeastOnce()).submit(any(ProducerWorker.class));
    }

    @Test
    public void testAddTask() throws ExecutionException, InterruptedException {
        Date currentTime = new Date();
        Task task = new Task("Task 1", " task to verify mock test ", currentTime, currentTime, currentTime, "NEW");
        when(producerExecutor.submit(any(ProducerWorker.class))).thenAnswer(new Answer<Future<StringBuilder>>() {
            public Future<StringBuilder> answer(InvocationOnMock invocation) throws Throwable {
                Future<StringBuilder> future = mock(FutureTask.class);
                when(future.isDone()).thenReturn(false, false, true);
                when(future.get()).thenReturn(new StringBuilder("This is a test"));
                return future;
            }
        });
        boolean result = taskDaoMockTest.add(task);
        assertEquals(result, Boolean.TRUE);
        verify(producerExecutor, atLeastOnce()).submit(any(ProducerWorker.class));
    }

    @Test
    public void testAddTaskWithNullElement() throws ExecutionException, InterruptedException {
        Date currentTime = new Date();
        Task task = null;
        when(producerExecutor.submit(any(ProducerWorker.class))).thenAnswer(new Answer<Future<StringBuilder>>() {
            public Future<StringBuilder> answer(InvocationOnMock invocation) throws Throwable {
                Future<StringBuilder> future = mock(FutureTask.class);
                when(future.isDone()).thenReturn(false, false, true);
                when(future.get()).thenReturn(new StringBuilder("This is a test"));
                return future;
            }
        });
        boolean result = taskDaoMockTest.add(task);
        assertEquals(result, Boolean.FALSE);
        verify(producerExecutor, times(0)).submit(any(ProducerWorker.class));
    }

    @Test
    public void testConsumeTask() {
        when(consumerExecutor.submit(any(ConsumerWorker.class))).thenAnswer(new Answer<Future<StringBuilder>>() {
            public Future<StringBuilder> answer(InvocationOnMock invocation) throws Throwable {
                Future<StringBuilder> future = mock(FutureTask.class);
                when(future.isDone()).thenReturn(false, false, true);
                when(future.get()).thenReturn(new StringBuilder("Trigger a test email to remind user about the task."));
                return future;
            }
        });
        StringBuilder sb = taskDaoMockTest.consumeTasks();
        assertTrue("String output length is greater than 0", (sb.length() > 0));
    }

    @Test
    public void testGetTask() {
        taskDaoMockTest.taskPriorityQueueInMemory = new PriorityBlockingQueue<Task>();
        for (int i = 0; i < 4; i++) {
            Date currentTime = new Date();
            taskDaoMockTest.taskPriorityQueueInMemory.add(new Task("Task 1", " task to verify mock test ", currentTime, currentTime, currentTime, "NEW"));
        }
        assertEquals(taskDaoMockTest.getTasks().size(), 4);
    }

    @Test
    public void testAddTasksWithNullElement() throws ExecutionException, InterruptedException {
        List<Task> mockTaskList = null;
        when(producerExecutor.submit(any(ProducerWorker.class))).thenAnswer(new Answer<Future<StringBuilder>>() {
            public Future<StringBuilder> answer(InvocationOnMock invocation) throws Throwable {
                Future<StringBuilder> future = mock(FutureTask.class);
                when(future.isDone()).thenReturn(false, false, true);
                when(future.get()).thenReturn(new StringBuilder("This is a test"));
                return future;
            }
        });
        boolean result = taskDaoMockTest.addTasks(mockTaskList);
        assertEquals(result, Boolean.FALSE);
        verify(producerExecutor, times(0)).submit(any(ProducerWorker.class));
    }

}
