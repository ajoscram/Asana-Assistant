package control;

import control.dtos.DevelopmentDTO;
import control.dtos.DevelopmentFilter;
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
import parse.ParseException;
import report.IReportPrinter;
import report.ReportException;

public interface IRouter {
    //users
    User login(String email, String password) throws ControlException;
    void registerUser(UserDTO user) throws ControlException;
    User getUser(long id) throws ControlException;
    DisplayString getAsigneeString(long taskId) throws ControlException;
    DisplayString getAdministratorString(long projectId) throws ControlException;
    List<DisplayString> getActiveUserStrings(long projectId) throws ControlException;
    List<DisplayString> getBannedUserStrings(long projectId) throws ControlException;
    
    //projects
    void addProject(ProjectDTO project) throws ControlException;
    Project getProject(long id) throws ControlException;
    List<DisplayString> getAdminProjectStrings(long userId) throws ControlException;
    List<DisplayString> getCollabProjectStrings(long userId) throws ControlException;
    void banUser(long projectId, long userId) throws ControlException;
    void unbanUser(long projectId, long userId) throws ControlException;
    void synchronize(long projectId, String filepath, TaskParser parser) throws ControlException, ParseException;
    void printReport(long projectId, String filepath, IReportPrinter printer) throws ControlException, ReportException;
    void printReport(long projectId, String filepath, IReportPrinter printer, TaskFilter taskFilter, DevelopmentFilter developmentFilter) throws ControlException, ReportException;
    
    //tasks
    Task getTask(long id) throws ControlException;
    List<DisplayString> getTaskStrings(long projectId) throws ControlException;
    List<DisplayString> getTaskStrings(long projectId, TaskFilter filter) throws ControlException;
    List<DisplayString> getSubtaskStrings(long taskId) throws ControlException;
    List<DisplayString> getSubtaskStrings(long taskId, TaskFilter filter) throws ControlException;
    
    //developments
    void addDevelopment(long taskId, DevelopmentDTO dto) throws ControlException;
    Development getDevelopment(long id) throws ControlException;
    List<DisplayString> getDevelopmentStrings(long taskId) throws ControlException;
    List<DisplayString> getDevelopmentStrings(long taskId, DevelopmentFilter filter) throws ControlException;
    
    //evidence
    Evidence getEvidence(long id) throws ControlException;
    List<DisplayString> getEvidenceStrings(long developmentId) throws ControlException;
    void downloadEvidence(long evidenceId, String path) throws ControlException;
}
