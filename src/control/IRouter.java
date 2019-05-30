package control;

import control.dtos.DevelopmentDTO;
import control.dtos.DisplayString;
import control.dtos.Filter;
import control.dtos.ProjectDTO;
import control.dtos.UserDTO;
import java.util.List;
import model.Development;
import model.Evidence;
import model.Project;
import model.Task;
import model.User;
import parse.ParseException;
import report.ReportException;

public interface IRouter {
    enum ParseFormat { JSON }
    enum PrintFormat { PDF }
    
    //users
    User login(String email, String password);
    void addUser(UserDTO user);
    User getUser(long id);
    DisplayString getAsigneeString(long taskId);
    DisplayString getAdministratorString(long projectId);
    List<DisplayString> getActiveUserStrings(long projectId);
    List<DisplayString> getBannedUserStrings(long projectId);
    
    //projects
    void addProject(ProjectDTO project);
    Project getProject(long id);
    List<DisplayString> getAdminProjectStrings(long userId);
    List<DisplayString> getCollabProjectStrings(long userId);
    void banUser(long projectId, long userId);
    void unbanUser(long projectId, long userId);
    void synchronize(long projectId, String filepath, ParseFormat format) throws ControlException, ParseException;
    void printReport(long projectId, String filepath, PrintFormat format) throws ControlException, ReportException;
    void printReport(long projectId, String filepath, PrintFormat format, Filter filter) throws ControlException, ReportException;
    
    //tasks
    Task getTask(long id);
    List<DisplayString> getTaskStrings(long projectId);
    List<DisplayString> getTaskStrings(long projectId, Filter filter);
    List<DisplayString> getSubtaskStrings(long taskId);
    List<DisplayString> getSubtaskStrings(long taskId, Filter filter);
    
    //developments
    void addDevelopment(long taskId, DevelopmentDTO dto);
    Development getDevelopment(long id);
    List<DisplayString> getDevelopmentStrings(long taskId);
    List<DisplayString> getDevelopmentStrings(long taskId, Filter filter);
    
    //evidence
    Evidence getEvidence(long id);
    List<DisplayString> getEvidenceStrings(long developmentId);
    void downloadEvidence(long evidenceId, String path);
}
