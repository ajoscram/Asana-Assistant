package control.controllers;

import control.ControlException;
import control.daos.TaskDAO;
import control.dtos.DisplayString;
import control.dtos.TaskDTO;
import control.dtos.TaskFilter;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskController {
    
    private TaskDAO dao;
    
    public TaskController(){
        dao = new TaskDAO();
    }
    
    public void addTask(long projectId, TaskDTO task) throws ControlException {
        dao.addTask(projectId, task);
    }
    
    public Task getTask(long id) throws ControlException {
        return dao.getTask(id);
    }
    
    public List<DisplayString> getTaskStrings(long projectId) throws ControlException {
        return getTaskStrings(projectId, TaskFilter.EMPTY);
    }
    
    //here filter only checks for task asignee
    public List<DisplayString> getTaskStrings(long projectId, TaskFilter filter) throws ControlException {
        List<DisplayString> strings = new ArrayList();
        List<Task> tasks;
        if(filter.getAsigneeId() != null && filter.getTaskId() == null)
            tasks = dao.getTasks(projectId, filter.getAsigneeId());
        else if(filter.getAsigneeId() == null && filter.getTaskId() != null){
            tasks = new ArrayList();
            tasks.add(dao.getTask(filter.getTaskId()));
        }
        else if(filter.getAsigneeId() != null && filter.getTaskId() != null){
            tasks = new ArrayList();
            for(Task task : dao.getTasks(projectId, filter.getAsigneeId()))
                if(task.getId() == filter.getTaskId())
                    tasks.add(task);
        }
        else
            tasks = dao.getTasks(projectId);
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
    
    public List<DisplayString> getSubtaskStrings(long id) throws ControlException {
        return getSubtaskStrings(id, TaskFilter.EMPTY);
    }
    
    //here filter should only check for task asignee, since task filtering
    //is only done at the first task level
    public List<DisplayString> getSubtaskStrings(long id, TaskFilter filter) throws ControlException {
        List<DisplayString> strings = new ArrayList();
        List<Task> subtasks;
        if(filter.getAsigneeId() != null)
            subtasks = dao.getSubtasks(id, filter.getAsigneeId());
        else
            subtasks = dao.getSubtasks(id);
        
        for(Task task : subtasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
}
