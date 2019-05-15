package control;

import java.util.List;
import control.controllers.*;
import control.dtos.*;
import model.*;
import parse.IParser;
import parse.parsers.JSONParser;
import report.IReportPrinter;
import report.printers.PDFReportPrinter;

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
    public List<DisplayString> getAdminProjectStrings(long userId) {
        return userController.getAdminProjectStrings(userId);
    }

    @Override
    public List<DisplayString> getCollabProjectStrings(long userId) {
        return userController.getCollabProjectStrings(userId);
    }

    @Override
    public void addProject(ProjectDTO dto) {
        projectController.addProject(dto);
    }

    @Override
    public Project getProject(long id) {
        return projectController.getProject(id);
    }

    @Override
    public DisplayString getAdministratorString(long projectId){
        return projectController.getAdministratorString(projectId);
    }
    
    @Override
    public List<DisplayString> getActiveUserStrings(long projectId) {
        return projectController.getActiveUserStrings(projectId);
    }

    @Override
    public List<DisplayString> getBannedUserStrings(long projectId) {
        return projectController.getBannedUserStrings(projectId);
    }

    @Override
    public List<DisplayString> getTaskStrings(long projectId) {
        return projectController.getTaskStrings(projectId);
    }

    @Override
    public void banUser(long projectId, long userId) {
        projectController.banUser(projectId, userId);
    }

    @Override
    public void unbanUser(long projectId, long userId) {
        projectController.unbanUser(projectId, userId);
    }

    private IParser<TaskDTO> getParser(IRouter.ParseFormat format){
        switch(format){
            case JSON:
                return new JSONParser<TaskDTO>();
            default:
                return null; //change to throw exception!
        }
    }
    
    @Override
    public void synchronizeTasks(long projectId, String filepath, ParseFormat format) {
        IParser parser = getParser(format);
        projectController.synchronizeTasks(projectId, filepath, parser);
    }
    
    private IReportPrinter getReportPrinter(IRouter.PrintFormat format){
        switch(format){
            case PDF:
                return new PDFReportPrinter();
            default:
                return null; //change to throw exception!
        }
    }
            
    @Override
    public void printReport(long projectId, String path, PrintFormat format) {
        IReportPrinter printer = getReportPrinter(format);
        projectController.printReport(projectId, path, printer);
    }

    @Override
    public Task getTask(long id) {
        return taskController.getTask(id);
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId) {
        return taskController.getSubtaskStrings(taskId);
    }

    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId) {
        return taskController.getDevelopmentStrings(taskId);
    }

    @Override
    public void addDevelopment(DevelopmentDTO dto) {
        developmentController.addDevelopment(dto);
    }

    @Override
    public Development getDevelopment(long id) {
        return developmentController.getDevelopment(id);
    }

    @Override
    public void addEvidence(long developmentId, String filepath){
        developmentController.addEvidence(developmentId, filepath);
    }
    
    @Override
    public List<DisplayString> getEvidenceStrings(long developmentId) {
        return developmentController.getEvidenceStrings(developmentId);
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
