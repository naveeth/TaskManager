package com.tasker.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by njanibasha on 9/4/17.
 */
public class Task implements Comparable<Task> {

    private String title;
    private String description;
    private Date reminderDate;
    private Date lastModifiedDate;
    private Date createdDate;
    private String status;

    public int compareTo(Task other) {
        return reminderDate.before(other.reminderDate) ? -1 : (reminderDate.after(other.reminderDate) ? 1 : 0);
    }

    public Task(String title, String description, Date reminderDate, Date lastModifiedDate, Date createdDate, String status) {
        this.title = title;
        this.description = description;
        this.reminderDate = reminderDate;
        this.lastModifiedDate = lastModifiedDate;
        this.createdDate = createdDate;
        this.status = status;
    }

    public Task() {

    }
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return "Title - " + title + " \nDescription - " + description + " \nreminderDate - " + sdf.format(reminderDate)
                + " \nlastModifiedDate - " + sdf.format(lastModifiedDate) + " \ncreatedDate - " + sdf.format(lastModifiedDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
