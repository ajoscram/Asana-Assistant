package control.controllers;

import control.daos.TaskDAO;
import control.dtos.DisplayString;
import control.dtos.Filter;
import control.dtos.TaskDTO;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskController {
    
    private TaskDAO dao;
    
    public TaskController(){
        dao = new TaskDAO();
    }
    
    public void addTask(long projectId, TaskDTO task){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Task getTask(long id){
        return dao.getTask(id);
    }
    
    public List<DisplayString> getTaskStrings(long projectId) {
        List<Task> tasks = dao.getTasks(projectId);
        List<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
    
    public List<DisplayString> getTaskStrings(long projectId, Filter filter) {
        List<Task> tasks = dao.getTasks(projectId, filter);
        List<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
    
    public List<DisplayString> getSubtaskStrings(long id){
        List<Task> subtasks = dao.getSubtasks(id);
        List<DisplayString> strings = new ArrayList();
        for(Task task : subtasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
}
