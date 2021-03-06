package alejandro;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.IRouter;
import asana_assistant_1.control.TaskParser;
import asana_assistant_1.control.dtos.DevelopmentDTO;
import asana_assistant_1.control.dtos.DevelopmentFilter;
import asana_assistant_1.control.dtos.DisplayString;
import asana_assistant_1.control.dtos.ProjectDTO;
import asana_assistant_1.control.dtos.TaskFilter;
import asana_assistant_1.control.dtos.UserDTO;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.model.Development;
import asana_assistant_1.model.Evidence;
import asana_assistant_1.model.Project;
import asana_assistant_1.model.Task;
import asana_assistant_1.model.User;
import asana_assistant_1.parse.ParseException;
import asana_assistant_1.report.IReportPrinter;
import asana_assistant_1.report.ReportException;

public class DummyRouter implements IRouter {

    ArrayList<User> users;
    ArrayList<User> active;
    ArrayList<User> banned;
    ArrayList<Project> adminProjects;
    ArrayList<Project> collabProjects;
    ArrayList<Task> tasks;
    ArrayList<Task> subtasks;
    ArrayList<Development> developments;
    ArrayList<Evidence> evidences;
    
    public DummyRouter(){
        
        User u1 = new User(69, 69, "Alejandro Schmidt", "ajoscram@gmail.com", true);
        User u2 = new User(1, 1, "Lorem Ipsum", "lorem@gmail.com", true);
        User u3 = new User(2, 2, "Gabriel Brenes", "jogabra@gmail.com", true);
        User u4 = new User(3, 3, "Carlos Solorzano", "cSolorzano@gmail.com", true);
        User u5 = new User(4, 4, "Rodrigo Villegas", "rVillegas@gmail.com", true);
        
        users = new ArrayList();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
        
        active = new ArrayList();
        active.add(u1);
        active.add(u2);
        active.add(u3);
        
        banned = new ArrayList();
        banned.add(u4);
        banned.add(u5);
        
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
        evidences.add(new Evidence(0, "0", "limpio_arriba1.png"));
        evidences.add(new Evidence(1, "1", "limpio_arriba2.png"));
        evidences.add(new Evidence(2, "2", "limpio_arriba3.png"));
        evidences.add(new Evidence(3, "3", "limpio_abajo.png"));
        evidences.add(new Evidence(4, "4", "plantas_humedas.jpeg"));
        evidences.add(new Evidence(5, "5", "plantas_humedas1.jpeg"));
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
        for(User user : active)
            strings.add(new DisplayString(user.getId(), user.getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getBannedUserStrings(long projectId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        for(User user : banned)
            strings.add(new DisplayString(user.getId(), user.getName()));
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
    public void banUser(long projectId, long userId) throws ControlException {
        for(User user : active){
            if(user.getId() == userId){
                active.remove(user);
                banned.add(user);
                return;
            }
        }
    }

    @Override
    public void unbanUser(long projectId, long userId) throws ControlException {
        for(User user : banned){
            if(user.getId() == userId){
                banned.remove(user);
                active.add(user);
                return;
            }
        }
    }

    @Override
    public void synchronize(long projectId, String filepath, TaskParser parser) throws ControlException, ParseException {
        System.out.println("File: " + filepath);
        System.out.println("ID: " + projectId);
        System.out.println("Format: " + parser.getClass().getName());
    }

    @Override
    public void printReport(long projectId, String filepath, IReportPrinter printer) throws ControlException, ReportException {
        System.out.println("Directory: " + filepath);
        System.out.println("ID: " + projectId);
        System.out.println("Format: " + printer.getClass().getName());
    }

    @Override
    public void printReport(long projectId, String filepath, IReportPrinter printer, TaskFilter taskFilter, DevelopmentFilter developmentFilter) throws ControlException, ReportException {
        System.out.println("Directory: " + filepath);
        System.out.println("ID: " + projectId);
        System.out.println("Format: " + printer.getClass().getName());
    }

    @Override
    public Task getTask(long id) throws ControlException {
        for(Task task : tasks)
            if(task.getId() == id)
                return task;
        for(Task task : subtasks)
            if(task.getId() == id)
                return task;
        throw new ControlException(ControlException.Type.IN_DEVELOPMENT);
    }

    @Override
    public List<DisplayString> getTaskStrings(long projectId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        for(Task task : tasks)
            strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getTaskStrings(long projectId, TaskFilter filter) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        for(Task task : tasks){
            if(filter.getTaskId() == null || (filter.getTaskId() != null && task.getId() == filter.getTaskId()))
                strings.add(new DisplayString(task.getId(), task.getName()));
        }
        return strings;
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        if(taskId == 0 || taskId == 1 || taskId ==2)
            for(Task task : subtasks)
                strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }

    @Override
    public List<DisplayString> getSubtaskStrings(long taskId, TaskFilter filter) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        if(taskId == 0 || taskId == 1 || taskId ==2)
            for(Task task : subtasks)
                strings.add(new DisplayString(task.getId(), task.getName()));
        return strings;
    }

    @Override
    public void addDevelopment(long taskId, DevelopmentDTO dto) throws ControlException {
        developments.add(new Development(developments.size(), dto.getDate(), dto.getHours(), dto.getDescription(), LocalDate.now()));
        for(String evidence : dto.getEvidenceFilepaths())
            evidences.add(new Evidence(evidences.size(), String.valueOf(evidences.size()), evidence));
    }

    @Override
    public Development getDevelopment(long id) throws ControlException {
        return developments.get(0);
    }

    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        String string;
        for(Development development : developments){
            string = "[" + development.getDate() + "] " + development.getDescription();
            strings.add(new DisplayString(development.getId(), string));
        }
        return strings;
    }

    @Override
    public List<DisplayString> getDevelopmentStrings(long taskId, DevelopmentFilter filter) throws ControlException {
        ArrayList<DisplayString> strings = new ArrayList();
        String string;
        for(Development development : developments){
            if((filter.getStart() == null || development.getDate().isAfter(filter.getStart()) || development.getDate().compareTo(filter.getStart()) == 0) &&
               (filter.getEnd() == null || development.getDate().isBefore(filter.getEnd()) || development.getDate().compareTo(filter.getEnd()) == 0)){
                
                string = "[" + development.getDate() + "] " + development.getDescription();
                strings.add(new DisplayString(development.getId(), string));
            }
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
    public void downloadEvidence(long evidenceId, String path) throws ControlException {
        System.out.println("Path: " + path);
        for(Evidence evidence : evidences)
            if(evidence.getId() == evidenceId)
                System.out.println("Evidence: " + evidence.getFilename());
    }
}
