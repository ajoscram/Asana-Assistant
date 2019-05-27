package control;

import control.daos.DevelopmentDAO;
import control.daos.TaskDAO;
import control.daos.UserDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Development;
import model.Project;
import model.Task;
import model.User;
import report.IReportBuilder;
import report.Report;
import report.Section;
import report.sections.ListSection;
import report.sections.TextSection;

public class ProjectReportBuilder implements IReportBuilder{

    private UserDAO userDAO;
    private TaskDAO taskDAO;
    private DevelopmentDAO developmentDAO;
    
    private Task task;
    private User asignee;
    private LocalDate start;
    private LocalDate end;
    
    public ProjectReportBuilder(){
        this.userDAO = new UserDAO();
        this.taskDAO = new TaskDAO();
        this.developmentDAO = new DevelopmentDAO();
    }
    
    public ProjectReportBuilder setAsignee(Long asigneeId) {
        if(asigneeId != null)
            this.asignee = userDAO.getUser(asigneeId);
        return this;
    }
    
    public ProjectReportBuilder setTask(Long taskId){
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
    
    private List<Section> getHeaderSections(Project project){
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
    
    private boolean validateAsignee(User taskAsignee){
        if(asignee == null)
            return true;
        else if((asignee != null && taskAsignee != null) &&
                (asignee.getId() == taskAsignee.getId()))
            return true;
        else
            return false;
    }
    
    private List<Section> getTaskSections(Task task, Task parent){
        ArrayList<Section> sections = new ArrayList();
        User taskAsignee = userDAO.getAsignee(task.getId());
        List<Development> developments = developmentDAO.getDevelopments(task.getId(), start, end);
        if(validateAsignee(taskAsignee)){
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
            //missing code!
            sections.add(taskSection);
        }
        for(Task subtask : taskDAO.getSubtasks(task.getId()))
            sections.addAll(getTaskSections(subtask, task));
        return sections;
    }
    
    private List<Section> getTaskSections(Task task){
        return getTaskSections(task, null);
    }
    
    @Override
    public Report build(Object object) {
        Project project = (Project)object;
        Report report = new Report();
        report.addSections(getHeaderSections(project));
        ListSection taskSections = new ListSection("tasks");
        if(task == null)
            for(Task task_ : taskDAO.getTasks(project.getId()))
                taskSections.addSections(getTaskSections(task_));
        else
            taskSections.addSections(getTaskSections(task));
        report.addSection(taskSections);
        return report;
    }

}
