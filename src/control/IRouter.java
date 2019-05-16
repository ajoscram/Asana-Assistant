package control;

import control.dtos.DevelopmentDTO;
import control.dtos.DisplayString;
import control.dtos.ProjectDTO;
import control.dtos.TaskFilter;
import control.dtos.UserDTO;
import java.util.List;
import model.Development;
import model.Evidence;
import model.Project;
import model.Task;
import model.User;

public interface IRouter {
    enum ParseFormat { JSON }
    enum PrintFormat { PDF }
    
    User login(String email, String password);
    void addUser(UserDTO user);
    User getUser(long id);
    List<DisplayString> getAdminProjectStrings(long userId);
    List<DisplayString> getCollabProjectStrings(long userId);
    
    void addProject(ProjectDTO project);
    Project getProject(long id);
    DisplayString getAdministratorString(long projectId);
    List<DisplayString> getActiveUserStrings(long projectId);
    List<DisplayString> getBannedUserStrings(long projectId);
    List<DisplayString> getTaskStrings(long projectId);
    List<DisplayString> getTaskStrings(long projectId, TaskFilter filter);
    void banUser(long projectId, long userId);
    void unbanUser(long projectId, long userId);
    void synchronizeTasks(long projectId, String filepath, ParseFormat format);
    void printReport(long projectId, String path, PrintFormat format);
    void printReport(long projectId, String path, PrintFormat format, TaskFilter filter);
    
    Task getTask(long id);
    List<DisplayString> getSubtaskStrings(long taskId);
    List<DisplayString> getDevelopmentStrings(long taskId);
    
    void addDevelopment(DevelopmentDTO dto);
    Development getDevelopment(long id);
    void addEvidence(long developmentId, String filepath);
    List<DisplayString> getEvidenceStrings(long developmentId);
    
    Evidence getEvidence(long id);
    void downloadEvidence(long evidenceId, String path);
}
