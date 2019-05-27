package control;

import java.util.List;
import control.controllers.*;
import control.dtos.*;
import model.*;

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
    public User login(String email, String password) {
        return userController.login(email, password);
    }

    @Override
    public void addUser(UserDTO user) {
        userController.addUser(user);
    }

    @Override
    public User getUser(long id) {
        return userController.getUser(id);
    }
    
    @Override
    public List<DisplayString> getActiveUserStrings(long projectId) {
        return userController.getActiveUserStrings(projectId);
    }

    @Override
    public List<DisplayString> getBannedUserStrings(long projectId) {
        return userController.getBannedUserStrings(projectId);
    }
    
    @Override
    public DisplayString getAdministratorString(long projectId){
        return userController.getAdministratorString(projectId);
    }
    
    @Override
    public DisplayString getAsigneeString(long taskId) {
        return userController.getAsigneeString(taskId);
    }

    //Projects
    @Override
    public void addProject(ProjectDTO dto) {
        projectController.addProject(dto);
    }

    @Override
    public Project getProject(long id) {
        return projectController.getProject(id);
    }

    @Override
    public List<DisplayString> getAdminProjectStrings(long userId) {
        return projectController.getAdminProjectStrings(userId);
    }

    @Override
    public List<DisplayString> getCollabProjectStrings(long userId) {
        return projectController.getCollabProjectStrings(userId);
    }
    
    @Override
    public void banUser(long projectId, long userId) {
        projectController.banUser(projectId, userId);
    }

    @Override
    public void unbanUser(long projectId, long userId) {
        projectController.unbanUser(projectId, userId);
    }

    @Override
    public void synchronize(long projectId, String filepath, ParseFormat format) throws ControlException {
        projectController.synchronize(projectId, filepath, format);
    }
              
    @Override
    public void printReport(long projectId, String path, PrintFormat format) throws ControlException {
        projectController.printReport(projectId, path, format);
    }
    
    @Override
    public void printReport(long projectId, String path, PrintFormat format, Filter filter) throws ControlException {
        projectController.printReport(projectId, path, format, filter);
    }
    
    //Tasks
    @Override
    public Task getTask(long id) {
        return taskController.getTask(id);
    }
    
    @Override
    public List<DisplayString> getTaskStrings(long projectId) {
        return taskController.getTaskStrings(projectId);
    }
    
    @Override
    public List<DisplayString> getTaskStrings(long projectId, Filter filter) {
        return taskController.getTaskStrings(projectId, filter);
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId) {
        return taskController.getSubtaskStrings(taskId);
    }
    
    @Override
    public List<DisplayString> getSubtaskStrings(long taskId, Filter filter) {
        return taskController.getSubtaskStrings(taskId, filter);
    }

    //Developments
    @Override
    public void addDevelopment(long taskId, DevelopmentDTO dto) {
        developmentController.addDevelopment(dto);
    }

    @Override
    public Development getDevelopment(long id) {
        return developmentController.getDevelopment(id);
    }
    
    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId) {
        return developmentController.getDevelopmentStrings(taskId);
    }
    
    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId, Filter filter) {
        return developmentController.getDevelopmentStrings(taskId, filter);
    }
    
    //Evidence
    @Override
    public List<DisplayString> getEvidenceStrings(long developmentId) {
        return evidenceController.getEvidenceStrings(developmentId);
    }

    @Override
    public Evidence getEvidence(long id) {
        return evidenceController.getEvidence(id);
    }

    @Override
    public void downloadEvidence(long evidenceId, String path) {
        evidenceController.downloadEvidence(evidenceId, path);
    }
 }
