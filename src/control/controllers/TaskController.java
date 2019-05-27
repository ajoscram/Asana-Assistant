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
        return getTaskStrings(projectId, null);
    }
    
    //here filter only checks for task asignee
    public List<DisplayString> getTaskStrings(long projectId, Filter filter) {
        List<Task> tasks;
        if(filter == null || filter.getAsigneeId() == null)
            tasks = dao.getTasks(projectId);
        else
            tasks = dao.getTasks(projectId, filter.getAsigneeId());
        List<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
    
    public List<DisplayString> getSubtaskStrings(long id){
        return getSubtaskStrings(id, null);
    }
    
    //here filter only checks for task asignee
    public List<DisplayString> getSubtaskStrings(long id, Filter filter){
        List<Task> subtasks;
        if(filter == null || filter.getAsigneeId() == null)
            subtasks = dao.getSubtasks(id);
        else
            subtasks = dao.getSubtasks(id);
        List<DisplayString> strings = new ArrayList();
        for(Task task : subtasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
}
