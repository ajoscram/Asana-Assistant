package asana_assistant_1.control;

import asana_assistant_1.model.Development;
import asana_assistant_1.model.User;
import asana_assistant_1.model.Project;
import asana_assistant_1.model.Task;
import asana_assistant_1.model.Evidence;
import asana_assistant_1.control.dtos.DevelopmentFilter;
import asana_assistant_1.control.dtos.DisplayString;
import asana_assistant_1.control.dtos.UserDTO;
import asana_assistant_1.control.dtos.DevelopmentDTO;
import asana_assistant_1.control.dtos.ProjectDTO;
import asana_assistant_1.control.dtos.TaskFilter;
import asana_assistant_1.control.controllers.EvidenceController;
import asana_assistant_1.control.controllers.DevelopmentController;
import asana_assistant_1.control.controllers.TaskController;
import asana_assistant_1.control.controllers.UserController;
import asana_assistant_1.control.controllers.ProjectController;
import java.util.List;
import asana_assistant_1.parse.ParseException;
import asana_assistant_1.report.IReportPrinter;
import asana_assistant_1.report.ReportException;

public class Router implements IRouter {

    private UserController userController;
    private ProjectController projectController;
    private TaskController taskController;
    private DevelopmentController developmentController;
    private EvidenceController evidenceController;
    
    public Router() {
        this.userController = new UserController();
        this.projectController = new ProjectController();
        this.taskController = new TaskController();
        this.developmentController = new DevelopmentController();
        this.evidenceController = new EvidenceController();
    }

    //Users
    @Override
    public User login(String email, String password) throws ControlException {
        return userController.login(email, password);
    }

    @Override
    public void registerUser(UserDTO user) throws ControlException {
        userController.registerUser(user);
    }

    @Override
    public User getUser(long id) throws ControlException{
        return userController.getUser(id);
    }
    
    @Override
    public List<DisplayString> getActiveUserStrings(long projectId) throws ControlException{
        return userController.getActiveUserStrings(projectId);
    }

    @Override
    public List<DisplayString> getBannedUserStrings(long projectId) throws ControlException{
        return userController.getBannedUserStrings(projectId);
    }
    
    @Override
    public DisplayString getAdministratorString(long projectId) throws ControlException{
        return userController.getAdministratorString(projectId);
    }
    
    @Override
    public DisplayString getAsigneeString(long taskId) throws ControlException{
        return userController.getAsigneeString(taskId);
    }

    //Projects
    @Override
    public void addProject(ProjectDTO dto) throws ControlException{
        projectController.addProject(dto);
    }

    @Override
    public Project getProject(long id) throws ControlException{
        return projectController.getProject(id);
    }

    @Override
    public List<DisplayString> getAdminProjectStrings(long userId) throws ControlException{
        return projectController.getAdminProjectStrings(userId);
    }

    @Override
    public List<DisplayString> getCollabProjectStrings(long userId) throws ControlException{
        return projectController.getCollabProjectStrings(userId);
    }
    
    @Override
    public void banUser(long projectId, long userId) throws ControlException{
        projectController.banUser(projectId, userId);
    }

    @Override
    public void unbanUser(long projectId, long userId) throws ControlException{
        projectController.unbanUser(projectId, userId);
    }

    @Override
    public void synchronize(long projectId, String filepath, TaskParser parser) throws ControlException, ParseException{
        projectController.synchronize(projectId, filepath, parser);
    }
              
    @Override
    public void printReport(long projectId, String filepath, IReportPrinter printer) throws ControlException, ReportException {
        projectController.printReport(projectId, filepath, printer);
    }
    
    @Override
    public void printReport(long projectId, String filepath, IReportPrinter printer, TaskFilter taskFilter, DevelopmentFilter developmentFilter) throws ControlException, ReportException {
        projectController.printReport(projectId, filepath, printer, taskFilter, developmentFilter);
    }
    
    //Tasks
    @Override
    public Task getTask(long id) throws ControlException{
        return taskController.getTask(id);
    }
    
    @Override
    public List<DisplayString> getTaskStrings(long projectId) throws ControlException{
        return taskController.getTaskStrings(projectId);
    }
    
    @Override
    public List<DisplayString> getTaskStrings(long projectId, TaskFilter filter) throws ControlException{
        return taskController.getTaskStrings(projectId, filter);
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId) throws ControlException{
        return taskController.getSubtaskStrings(taskId);
    }
    
    @Override
    public List<DisplayString> getSubtaskStrings(long taskId, TaskFilter filter) throws ControlException{
        return taskController.getSubtaskStrings(taskId, filter);
    }

    //Developments
    @Override
    public void addDevelopment(long taskId, DevelopmentDTO dto) throws ControlException{
        developmentController.addDevelopment(taskId,dto);
    }

    @Override
    public Development getDevelopment(long id) throws ControlException{
        return developmentController.getDevelopment(id);
    }
    
    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId) throws ControlException{
        return developmentController.getDevelopmentStrings(taskId);
    }
    
    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId, DevelopmentFilter filter) throws ControlException{
        return developmentController.getDevelopmentStrings(taskId, filter);
    }
    
    //Evidence
    @Override
    public List<DisplayString> getEvidenceStrings(long developmentId) throws ControlException{
        return evidenceController.getEvidenceStrings(developmentId);
    }

    @Override
    public Evidence getEvidence(long id) throws ControlException{
        return evidenceController.getEvidence(id);
    }

    @Override
    public void downloadEvidence(long evidenceId, String path) throws ControlException{
        evidenceController.downloadEvidence(evidenceId, path);
    }
 }
