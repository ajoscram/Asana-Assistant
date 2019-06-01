package control;

import java.util.List;
import control.controllers.*;
import control.dtos.*;
import model.*;
import parse.ParseException;
import report.ReportException;

public class Router implements IRouter {
    private static Router INSTANCE;

    private UserController userController;
    private ProjectController projectController;
    private TaskController taskController;
    private DevelopmentController developmentController;
    private EvidenceController evidenceController;
    
    private Router() {
        this.userController = new UserController();
        this.projectController = new ProjectController();
        this.taskController = new TaskController();
        this.developmentController = new DevelopmentController();
        this.evidenceController = new EvidenceController();
    }

    public static Router getInstance() {
        if(INSTANCE == null)
            INSTANCE = new Router();
        return INSTANCE;
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
    public void synchronize(long projectId, String filepath, ParseFormat format) throws ControlException, ParseException{
        projectController.synchronize(projectId, filepath, format);
    }
              
    @Override
    public void printReport(long projectId, String filepath, PrintFormat format) throws ControlException, ReportException {
        projectController.printReport(projectId, filepath, format);
    }
    
    @Override
    public void printReport(long projectId, String filepath, PrintFormat format, Filter filter) throws ControlException, ReportException {
        projectController.printReport(projectId, filepath, format, filter);
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
    public List<DisplayString> getTaskStrings(long projectId, Filter filter) throws ControlException{
        return taskController.getTaskStrings(projectId, filter);
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId) throws ControlException{
        return taskController.getSubtaskStrings(taskId);
    }
    
    @Override
    public List<DisplayString> getSubtaskStrings(long taskId, Filter filter) throws ControlException{
        return taskController.getSubtaskStrings(taskId, filter);
    }

    //Developments
    @Override
    public void addDevelopment(long taskId, DevelopmentDTO dto) throws ControlException{
        developmentController.addDevelopment(dto);
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
    public List<DisplayString> getDevelopmentStrings(long taskId, Filter filter) throws ControlException{
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
