package control.controllers;

import control.ControlException;
import control.IRouter;
import control.daos.ProjectDAO;
import control.dtos.DisplayString;
import control.dtos.Filter;
import control.dtos.ProjectDTO;
import java.util.List;
import model.Project;
import control.dtos.TaskDTO;
import java.util.ArrayList;
import parse.IParser;
import parse.parsers.JSONParser;
import report.IReportPrinter;
import report.printers.PDFReportPrinter;

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
    
    public List<DisplayString> getAdminProjectStrings(long id){
        List<Project> projects = dao.getAdminProjects(id);
        List<DisplayString> strings = new ArrayList();
        for(Project project : projects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }
    
    public List<DisplayString> getCollabProjectStrings(long id){
        List<Project> projects = dao.getCollabProjects(id);
        List<DisplayString> strings = new ArrayList();
        for(Project project : projects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }
    
    public void banUser(long projectId, long userId){
        dao.banUser(projectId, userId);
    }
    
    public void unbanUser(long projectId, long userId){
        dao.unbanUser(projectId, userId);
    }
    
    private IParser<TaskDTO> getParser(IRouter.ParseFormat format) throws ControlException {
        switch(format){
            case JSON:
                return new JSONParser<TaskDTO>();
            default:
                throw new ControlException(ControlException.Type.UNKNOWN_PARSER_TYPE);
        }
    }
    
    public void synchronize(long projectId, String filepath, IRouter.ParseFormat format){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private IReportPrinter getReportPrinter(IRouter.PrintFormat format) throws ControlException {
        switch(format){
            case PDF:
                return new PDFReportPrinter();
            default:
                throw new ControlException(ControlException.Type.UNKNOWN_PRINTER_TYPE);
        }
    }
    
    public void printReport(long id, String path, IRouter.PrintFormat format){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void printReport(long id, String path, IRouter.PrintFormat format, Filter filter){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
