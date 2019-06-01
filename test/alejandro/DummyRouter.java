package alejandro;

import control.ControlException;
import control.IRouter;
import control.dtos.DevelopmentDTO;
import control.dtos.DisplayString;
import control.dtos.Filter;
import control.dtos.ProjectDTO;
import control.dtos.UserDTO;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import model.Development;
import model.Evidence;
import model.Project;
import model.Task;
import model.User;
import parse.ParseException;
import report.ReportException;

public class DummyRouter implements IRouter {

    ArrayList<User> users;
    ArrayList<Project> adminProjects;
    ArrayList<Project> collabProjects;
    ArrayList<Task> tasks;
    ArrayList<Task> subtasks;
    ArrayList<Development> developments;
    ArrayList<Evidence> evidences;
    
    public DummyRouter(){
        users = new ArrayList();
        users.add(new User(69, 69, "Alejandro Schmidt", "ajoscram@gmail.com", true));
        
        adminProjects = new ArrayList();
        adminProjects.add(new Project(0, "Proyecto 1 Diseño", LocalDate.now()));
        adminProjects.add(new Project(1, "Proyecto 2 Diseño", LocalDate.now().plusDays(2)));
        adminProjects.add(new Project(2, "Proyecto 3 Diseño", LocalDate.now().plusDays(3)));
        
        collabProjects = new ArrayList();
        collabProjects.add(new Project(3, "Proyecto 1 Móviles", LocalDate.now().plusDays(4)));
        collabProjects.add(new Project(4, "Proyecto 2 Móviles", LocalDate.now().plusDays(5)));
        
        tasks = new ArrayList();
        tasks.add(new Task(0, "Mantenimiento", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 30), null, Task.Type.SECTION));
        tasks.add(new Task(1, "Limpiar Hojas", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 31), null, Task.Type.SINGLE));
        tasks.add(new Task(2, "Regar Plantas", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 30), null, Task.Type.SINGLE));
        
        subtasks = new ArrayList();
        subtasks.add(new Task(3, "Sacar Manguera", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 27), null, Task.Type.SUBTASK));
        subtasks.add(new Task(4, "Conectar Manguera", LocalDate.of(2019, Month.MAY, 28), LocalDate.of(2019, Month.MAY, 28), null, Task.Type.SUBTASK));
        subtasks.add(new Task(5, "Abrir Tubo", LocalDate.of(2019, Month.MAY, 28), LocalDate.of(2019, Month.MAY, 29), null, Task.Type.SUBTASK));
        subtasks.add(new Task(6, "Cerrar Tubo", LocalDate.of(2019, Month.MAY, 28), LocalDate.of(2019, Month.MAY, 30), null, Task.Type.SUBTASK));
        
        developments = new ArrayList();
        developments.add(new Development(0, LocalDate.of(2019, Month.MAY, 28), 3, "Hojas limpias arriba.", LocalDate.of(2019, Month.MAY, 28)));
        developments.add(new Development(1, LocalDate.of(2019, Month.MAY, 29), 2, "Hojas limpias abajo.", LocalDate.of(2019, Month.MAY, 29)));
        developments.add(new Development(2, LocalDate.of(2019, Month.MAY, 28), 3, "Plantas Regadas", LocalDate.of(2019, Month.MAY, 28)));
        developments.add(new Development(3, LocalDate.of(2019, Month.MAY, 30), 3, "Tubo cerrado.", LocalDate.of(2019, Month.MAY, 30)));
        
        evidences = new ArrayList();
        evidences.add(new Evidence(0, "limpio_arriba1.png"));
        evidences.add(new Evidence(1, "limpio_arriba2.png"));
        evidences.add(new Evidence(2, "limpio_arriba3.png"));
        evidences.add(new Evidence(3, "limpio_abajo.png"));
        evidences.add(new Evidence(4, "plantas_humedas.jpeg"));
        evidences.add(new Evidence(5, "plantas_humedas1.jpeg"));
    }
    
    @Override
    public User login(String email, String password) throws ControlException {
        return users.get(0);
    }

    @Override
    public void registerUser(UserDTO user) throws ControlException {}

    @Override
    public User getUser(long id) throws ControlException {
        return users.get(0);
    }

    @Override
    public DisplayString getAsigneeString(long taskId) throws ControlException {
        return new DisplayString(users.get(0).getId(), users.get(0).getName());
    }

    @Override
    public DisplayString getAdministratorString(long projectId) throws ControlException {
        return new DisplayString(users.get(0).getId(), users.get(0).getName());
    }

    @Override
    public List<DisplayString> getActiveUserStrings(long projectId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        strings.add(new DisplayString(users.get(0).getId(), users.get(0).getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getBannedUserStrings(long projectId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        strings.add(new DisplayString(users.get(0).getId(), users.get(0).getName()));
        return strings;
    }

    @Override
    public void addProject(ProjectDTO project) throws ControlException {
        adminProjects.add(new Project(adminProjects.size(), project.getName(), LocalDate.now()));
    }

    @Override
    public Project getProject(long id) throws ControlException {
        return adminProjects.get(0);
    }

    @Override
    public List<DisplayString> getAdminProjectStrings(long userId) throws ControlException {
        List<DisplayString> strings = new ArrayList();
        for(Project project : adminProjects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getCollabProjectStrings(long userId) throws ControlException {
        List<DisplayString> strings = new ArrayList();
        for(Project project : collabProjects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }

    @Override
    public void banUser(long projectId, long userId) throws ControlException {}

    @Override
    public void unbanUser(long projectId, long userId) throws ControlException {}

    @Override
    public void synchronize(long projectId, String filepath, ParseFormat format) throws ControlException, ParseException {}

    @Override
    public void printReport(long projectId, String filepath, PrintFormat format) throws ControlException, ReportException {}

    @Override
    public void printReport(long projectId, String filepath, PrintFormat format, Filter filter) throws ControlException, ReportException {}

    @Override
    public Task getTask(long id) throws ControlException {
        return tasks.get(0);
    }

    @Override
    public List<DisplayString> getTaskStrings(long projectId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getTaskStrings(long projectId, Filter filter) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        for(Task task : subtasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId, Filter filter) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        for(Task task : subtasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }

    @Override
    public void addDevelopment(long taskId, DevelopmentDTO dto) throws ControlException {}

    @Override
    public Development getDevelopment(long id) throws ControlException {
        return developments.get(0);
    }

    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        String string;
        for(Development development : developments){
            string = "[" + development.getDate() + "]" + development.getDescription();
            strings.add(new DisplayString(development.getId(), string));
        }
        return strings;
    }

    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId, Filter filter) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        String string;
        for(Development development : developments){
            string = "[" + development.getDate() + "]" + development.getDescription();
            strings.add(new DisplayString(development.getId(), string));
        }
        return strings;
    }

    @Override
    public Evidence getEvidence(long id) throws ControlException {
        return evidences.get(0);
    }

    @Override
    public List<DisplayString> getEvidenceStrings(long developmentId) throws ControlException {
        List<DisplayString> strings = new ArrayList();
        for(Evidence evidence : evidences)
            strings.add(new DisplayString(evidence.getId(), evidence.getFilename()));
        return strings;
    }

    @Override
    public void downloadEvidence(long evidenceId, String path) throws ControlException {}
}
