package asana_assistant_1.control.controllers;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.daos.ProjectDAO;
import asana_assistant_1.control.daos.TaskDAO;
import asana_assistant_1.control.dtos.DisplayString;
import asana_assistant_1.control.dtos.TaskFilter;
import asana_assistant_1.control.dtos.DevelopmentFilter;
import asana_assistant_1.control.dtos.ProjectDTO;
import java.util.List;
import asana_assistant_1.model.Project;
import asana_assistant_1.control.dtos.TaskDTO;
import java.util.ArrayList;
import asana_assistant_1.control.ProjectReportBuilder;
import asana_assistant_1.control.TaskParser;
import asana_assistant_1.parse.ParseException;
import asana_assistant_1.report.IReportPrinter;
import asana_assistant_1.report.Report;
import asana_assistant_1.report.ReportException;

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
    
    public void synchronize(long projectId, String filepath, TaskParser parser) throws ControlException, ParseException{
        List<TaskDTO> tasks = parser.parse(filepath);
        TaskDAO taskDAO = new TaskDAO();
        for(TaskDTO task : tasks)
            taskDAO.addTask(projectId, task);
    }
    
    public void printReport(long id, String filepath, IReportPrinter printer) throws ControlException, ReportException{
        ProjectReportBuilder reportBuilder = new ProjectReportBuilder();
        Project project = dao.getProject(id);
        Report report = reportBuilder.build(project);
        printer.print(report, filepath);
    }
    
    public void printReport(long id, String filepath, IReportPrinter printer, TaskFilter taskFilter, DevelopmentFilter developmentFilter) throws ControlException, ReportException {
        ProjectReportBuilder reportBuilder = new ProjectReportBuilder()
                .setAsignee(taskFilter.getAsigneeId())
                .setTask(taskFilter.getTaskId())
                .setStart(developmentFilter.getStart())
                .setEnd(developmentFilter.getEnd());
        Project project = dao.getProject(id);
        Report report = reportBuilder.build(project);
        printer.print(report, filepath);
    }
}
