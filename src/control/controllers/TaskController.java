package control.controllers;

import control.daos.TaskDAO;
import control.dtos.DisplayString;
import java.util.ArrayList;
import java.util.List;
import model.Development;
import model.Task;
import model.User;

public class TaskController {
    
    private TaskDAO dao;
    
    public TaskController(){
        dao = new TaskDAO();
    }
    
    public Task getTask(long id){
        return dao.getTask(id);
    }
    
    public DisplayString getAsigneeString(long id){
        User user = dao.getAsignee(id);
        return new DisplayString(user.getId(), user.getName());
    }
    
    public List<DisplayString> getSubtaskStrings(long id){
        List<Task> subtasks = dao.getSubtasks();
        List<DisplayString> strings = new ArrayList();
        for(Task task : subtasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
    
    public List<DisplayString> getDevelopmentStrings(long id){
        List<Development> developments = dao.getDevelopments(id);
        List<DisplayString> strings = new ArrayList();
        String string;
        for(Development development : developments){
            string = "[" + development.getDate() + "]" + development.getDescription();
            strings.add(new DisplayString(development.getId(), string));
        }
        return strings;
    }
}
