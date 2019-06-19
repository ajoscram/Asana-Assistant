package asana_assistant_1.control;

import asana_assistant_1.control.daos.DevelopmentDAO;
import asana_assistant_1.control.daos.EvidenceDAO;
import asana_assistant_1.control.daos.TaskDAO;
import asana_assistant_1.control.daos.UserDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.model.Development;
import asana_assistant_1.model.Evidence;
import asana_assistant_1.model.Project;
import asana_assistant_1.model.Task;
import asana_assistant_1.model.User;
import asana_assistant_1.report.IReportBuilder;
import asana_assistant_1.report.Report;
import asana_assistant_1.report.ReportException;
import asana_assistant_1.report.Section;
import asana_assistant_1.report.sections.ListSection;
import asana_assistant_1.report.sections.TextSection;

public class ProjectReportBuilder implements IReportBuilder{

    private UserDAO userDAO;
    private TaskDAO taskDAO;
    private DevelopmentDAO developmentDAO;
    private EvidenceDAO evidenceDAO;
    
    private Task task;
    private User asignee;
    private LocalDate start;
    private LocalDate end;
    
    public ProjectReportBuilder(UserDAO userDAO, TaskDAO taskDAO, DevelopmentDAO developmentDAO, EvidenceDAO evidenceDAO){
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
        this.developmentDAO = developmentDAO;
        this.evidenceDAO = evidenceDAO;
    }
    
    public ProjectReportBuilder(){
        this.userDAO = new UserDAO();
        this.taskDAO = new TaskDAO();
        this.developmentDAO = new DevelopmentDAO();
        this.evidenceDAO = new EvidenceDAO();
    }
    
    public ProjectReportBuilder setAsignee(Long asigneeId) throws ControlException{
        if(asigneeId != null)
            this.asignee = userDAO.getUser(asigneeId);
        return this;
    }
    
    public ProjectReportBuilder setTask(Long taskId) throws ControlException {
        if(taskId != null)
            this.task = taskDAO.getTask(taskId);
        return this;
    }
    
    public ProjectReportBuilder setStart(LocalDate start){
        this.start = start;
        return this;
    }
    
    public ProjectReportBuilder setEnd(LocalDate end){
        this.end = end;
        return this;
    }
    
    protected boolean validateAsignee(User taskAsignee){
        if(asignee == null)
            return true;
        else if((asignee != null && taskAsignee != null) &&
                (asignee.getId() == taskAsignee.getId()))
            return true;
        else
            return false;
    }
    
    protected boolean validateTask(Task task){
        return this.task == null || (this.task != null && task.getId() == this.task.getId());
    }
    
    protected List<Section> getHeaderSections(Project project){
        ArrayList<Section> sections = new ArrayList();
        sections.add(new TextSection("name", project.getName()));
        sections.add(new TextSection("id", Long.toString(project.getId())));
        if(asignee != null)
            sections.add(new TextSection("by", asignee.getName()));
        if(start != null)
            sections.add(new TextSection("start", start.format(DateTimeFormatter.ISO_DATE)));
        if(end != null)
            sections.add(new TextSection("finish", end.format(DateTimeFormatter.ISO_DATE)));
        return sections;
    }
    
    protected ListSection getDevelopmentSection(Development development) throws ControlException{
        ListSection developmentSection = new ListSection(development.getDate().format(DateTimeFormatter.ISO_DATE));
        developmentSection.addSection(new TextSection("description", development.getDescription()));
        developmentSection.addSection(new TextSection("work", development.getHours() + " hours"));
        ListSection evidenceSection = new ListSection("evidence");
        for(Evidence evidence : evidenceDAO.getEvidences(development.getId()))
            evidenceSection.addSection(new TextSection(null, evidence.getFilename()));
        developmentSection.addSection(evidenceSection);
        return developmentSection;
    }
    
    protected List<Section> getTaskSections(Task task, Task parent) throws ControlException{
        ArrayList<Section> sections = new ArrayList();
        User taskAsignee = userDAO.getAsignee(task.getId());
        if(validateAsignee(taskAsignee) && validateTask(task)){
            ListSection taskSection = new ListSection(task.getName());
            taskSection.addSection(new TextSection("type", task.getType().toString()));
            if(parent != null)
                taskSection.addSection(new TextSection("parent", parent.getName()));
            if(taskAsignee != null  && asignee == null)
                taskSection.addSection(new TextSection("asignee", taskAsignee.getName()));
            if(task.getDue() != null)
                taskSection.addSection(new TextSection("due", task.getDue().format(DateTimeFormatter.ISO_DATE)));
            if(task.getCompleted() != null)
                taskSection.addSection(new TextSection("completed", task.getCompleted().format(DateTimeFormatter.ISO_DATE)));
            for(Development development : developmentDAO.getDevelopments(task.getId(), start, end))
                taskSection.addSection(getDevelopmentSection(development));
            sections.add(taskSection);
        }
        for(Task subtask : taskDAO.getSubtasks(task.getId()))
            sections.addAll(getTaskSections(subtask, task));
        return sections;
    }
    
    protected List<Section> getTaskSections(Task task) throws ControlException{
        return getTaskSections(task, null);
    }
    
    @Override
    public Report build(Object object) throws ReportException{
        try{
            Project project = (Project)object;
            Report report = new Report();
            report.addSections(getHeaderSections(project));
            ListSection taskSections = new ListSection("tasks");
            for(Task task_ : taskDAO.getTasks(project.getId()))
                taskSections.addSections(getTaskSections(task_));
            report.addSection(taskSections);
            return report;
        }catch(ControlException ce){
            throw new ReportException(ReportException.Type.FILE_IO_ERROR);
        }
    }

}
