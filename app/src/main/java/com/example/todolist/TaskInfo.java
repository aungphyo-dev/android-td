package com.example.todolist;

public class TaskInfo {
    public String taskName;
    public boolean taskStatus;
    public TaskInfo(){
        super();
    }
    public TaskInfo(String taskName, boolean taskStatus){
        super();
        this.taskName = taskName;
        this.taskStatus=taskStatus;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public boolean isTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }
}
