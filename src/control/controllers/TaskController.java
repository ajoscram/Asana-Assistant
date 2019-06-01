package control.controllers;

import control.ControlException;
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
    
    public void addTask(long projectId, TaskDTO task) throws ControlException {
        dao.addTask(projectId, task);
    }
    
    public Task getTask(long id) throws ControlException {
        return dao.getTask(id);
    }
    
    public List<DisplayString> getTaskStrings(long projectId) throws ControlException {
        return getTaskStrings(projectId, null);
    }
    
    //here filter only checks for task asignee
    public List<DisplayString> getTaskStrings(long projectId, Filter filter) throws ControlException {
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
    
    public List<DisplayString> getSubtaskStrings(long id) throws ControlException {
        return getSubtaskStrings(id, null);
    }
    
    //here filter only checks for task asignee
    public List<DisplayString> getSubtaskStrings(long id, Filter filter) throws ControlException {
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
