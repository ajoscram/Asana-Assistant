package control.controllers;

import control.daos.ProjectDAO;
import control.dtos.DisplayString;
import control.dtos.ProjectDTO;
import java.util.List;
import model.Project;
import control.dtos.TaskDTO;
import control.dtos.TaskFilter;
import java.util.ArrayList;
import model.Task;
import model.User;
import parse.IParser;
import report.IReportPrinter;

public class ProjectController {
    
    private ProjectDAO dao;
    
    public ProjectController(){
        dao = new ProjectDAO();
    }
    
    public void addProject(ProjectDTO dto){
        dao.addProject(dto);
    }
    
    public Project getProject(long id){
        return dao.getProject(id);
    }
    
    public void banUser(long projectId, long userId){
        dao.banUser(projectId, userId);
    }
    
    public void unbanUser(long projectId, long userId){
        dao.unbanUser(projectId, userId);
    }
    
    public DisplayString getAdministratorString(long id) {
        User administrator = dao.getAdministrator(id);
        return new DisplayString(administrator.getId(), administrator.getName());
    }
    
    public List<DisplayString> getActiveUserStrings(long id) {
        List<User> users = dao.getActiveUsers(id);
        List<DisplayString> strings = new ArrayList();
        String string;
        for(User user : users){
            string = user.getName() + " (" + user.getEmail() + ")";
            strings.add(new DisplayString(user.getId(), string));
        }
        return strings;
    }
    
    public List<DisplayString> getBannedUserStrings(long id) {
        List<User> users = dao.getBannedUsers(id);
        List<DisplayString> strings = new ArrayList();
        String string;
        for(User user : users){
            string = user.getName() + " (" + user.getEmail() + ")";
            strings.add(new DisplayString(user.getId(), string));
        }
        return strings;
    }
    
    public List<DisplayString> getTaskStrings(long id) {
        List<Task> tasks = dao.getTasks(id);
        List<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
    
    public List<DisplayString> getTaskStrings(long id, TaskFilter filter) {
        List<Task> tasks = dao.getTasks(id, filter);
        List<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }
    
    private void addTask(long projectId, TaskDTO task){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void synchronizeTasks(long projectId, String filepath, IParser<TaskDTO> parser){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void printReport(long id, String path, IReportPrinter printer){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void printReport(long id, String path, IReportPrinter printer, TaskFilter filter){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
