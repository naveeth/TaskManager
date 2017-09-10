package com.tasker.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tasker.dao.TaskDao;
import com.tasker.model.Task;

/**
 * Created by njanibasha on 9/8/17.
 */

public class TaskServiceTest {

    @InjectMocks
    TaskService taskServiceTest;

    @Mock
    TaskDao taskDao;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTasks() throws Exception {
        List<Task> mockTaskList = new ArrayList<Task>();
        for (int i = 0; i < 4; i++) {
            Date currentTime = new Date();
            mockTaskList.add(new Task("Task " + i, " task to verify mock test ", currentTime, currentTime, currentTime, "NEW"));
        }
        when(taskDao.addTasks(mockTaskList)).thenReturn(Boolean.TRUE);
        assertEquals(taskDao.addTasks(mockTaskList), Boolean.TRUE);
        boolean result = taskServiceTest.addTasks(mockTaskList);
        assertEquals(result, Boolean.TRUE);
    }

    @Test
    public void testAddTasksNull() throws Exception {
        List<Task> mockTaskList = null;
        when(taskDao.addTasks(mockTaskList)).thenReturn(Boolean.FALSE);
        boolean result = taskServiceTest.addTasks(mockTaskList);
        assertEquals(result, Boolean.FALSE);
        // confirm that taskDao.addTasks is not called for this case
        verify(taskDao, times(0)).addTasks(mockTaskList);
    }

    @Test
    public void testAddTask() {
        Date currentDate = new Date();
        Task task = new Task("Task 1", "This task is to verify mock test", currentDate, currentDate, currentDate, "NEW");
        when(taskDao.add(task)).thenReturn(Boolean.TRUE);
        boolean result = taskServiceTest.addTask(task);
        assertEquals(result, Boolean.TRUE);
    }

    @Test
    public void testAddTaskWithNullInput() {
        Task task = null;
        when(taskDao.add(task)).thenReturn(Boolean.FALSE);
        boolean result = taskServiceTest.addTask(task);
        verify(taskDao, times(0)).add(task);
    }

}
