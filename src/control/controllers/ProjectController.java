package control.controllers;

import control.ControlException;
import control.IRouter;
import control.daos.ProjectDAO;
import control.daos.TaskDAO;
import control.dtos.DisplayString;
import control.dtos.Filter;
import control.dtos.ProjectDTO;
import java.util.List;
import model.Project;
import control.dtos.TaskDTO;
import java.util.ArrayList;
import parse.IParser;
import control.JSONTaskParser;
import control.ProjectReportBuilder;
import parse.ParseException;
import report.IReportPrinter;
import report.Report;
import report.ReportException;
import report.printers.PDFReportPrinter;

public class ProjectController {
    
    private ProjectDAO dao;
    
    public ProjectController(){
        dao = new ProjectDAO();
    }
    
    public void addProject(ProjectDTO dto) throws ControlException {
        dao.addProject(dto);
    }
    
    public Project getProject(long id) throws ControlException {
        return dao.getProject(id);
    }
    
    public List<DisplayString> getAdminProjectStrings(long id) throws ControlException {
        List<Project> projects = dao.getAdminProjects(id);
        List<DisplayString> strings = new ArrayList();
        for(Project project : projects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }
    
    public List<DisplayString> getCollabProjectStrings(long id) throws ControlException {
        List<Project> projects = dao.getCollabProjects(id);
        List<DisplayString> strings = new ArrayList();
        for(Project project : projects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }
    
    public void banUser(long projectId, long userId) throws ControlException {
        dao.banUser(projectId, userId);
    }
    
    public void unbanUser(long projectId, long userId) throws ControlException {
        dao.unbanUser(projectId, userId);
    }
    
    private IParser<List<TaskDTO>> getParser(IRouter.ParseFormat format) throws ControlException {
        switch(format){
            case JSON:
                return new JSONTaskParser();
            default:
                throw new ControlException(ControlException.Type.UNKNOWN_PARSER_TYPE);
        }
    }
    
    public void synchronize(long projectId, String filepath, IRouter.ParseFormat format) throws ControlException, ParseException{
        IParser<List<TaskDTO>> parser = getParser(format);
        List<TaskDTO> tasks = parser.parse(filepath);
        TaskDAO taskDAO = new TaskDAO();
        for(TaskDTO task : tasks)
            taskDAO.addTask(projectId, task);
    }
    
    private IReportPrinter getReportPrinter(IRouter.PrintFormat format) throws ControlException {
        switch(format){
            case PDF:
                return new PDFReportPrinter();
            default:
                throw new ControlException(ControlException.Type.UNKNOWN_PRINTER_TYPE);
        }
    }
    
    public void printReport(long id, String filepath, IRouter.PrintFormat format) throws ControlException, ReportException{
        ProjectReportBuilder reportBuilder = new ProjectReportBuilder();
        Project project = dao.getProject(id);
        Report report = reportBuilder.build(project);
        IReportPrinter printer = getReportPrinter(format);
        printer.print(report, filepath);
    }
    
    public void printReport(long id, String filepath, IRouter.PrintFormat format, Filter filter) throws ControlException, ReportException {
        ProjectReportBuilder reportBuilder = new ProjectReportBuilder()
                .setAsignee(filter.getAsigneeId())
                .setTask(filter.getTaskId())
                .setStart(filter.getStart())
                .setEnd(filter.getEnd());
        Project project = dao.getProject(id);
        Report report = reportBuilder.build(project);
        IReportPrinter printer = getReportPrinter(format);
        printer.print(report, filepath);
    }
}
