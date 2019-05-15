package control.controllers;

import control.dtos.DisplayString;
import control.dtos.ProjectDTO;
import java.util.List;
import model.Project;
import control.dtos.TaskDTO;
import parse.IParser;
import report.IReportPrinter;

public class ProjectController {
    
    public ProjectController(){}
    
    public void addProject(ProjectDTO dto){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Project getProject(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void banUser(long projectId, long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void unbanUser(long projectId, long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public DisplayString getAdministratorString(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getActiveUserStrings(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getBannedUserStrings(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getTaskStrings(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
