package gabriel;

import asana_assistant_1.control.daos.connection.Connection;
import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.daos.DevelopmentDAO;
import asana_assistant_1.control.daos.EvidenceDAO;
import asana_assistant_1.control.daos.ProjectDAO;
import asana_assistant_1.control.daos.TaskDAO;
import asana_assistant_1.control.daos.UserDAO;
import asana_assistant_1.control.dtos.DevelopmentDTO;
import asana_assistant_1.control.dtos.ProjectDTO;
import asana_assistant_1.control.dtos.TaskDTO;
import asana_assistant_1.control.dtos.UserDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import asana_assistant_1.model.Development;
import asana_assistant_1.model.Evidence;
import asana_assistant_1.model.User;
import asana_assistant_1.model.Project;
import asana_assistant_1.model.Task;



public class GabrielMain {

    public static void main(String[] args) {
        /*try{
            Connection.connect();
            String date1 = "2019-06-08";
            LocalDate datecreated1 = LocalDate.parse(date1);
            String date2 = "2019-06-05";
            LocalDate datecreated2 = LocalDate.parse(date2);
            DevelopmentDAO developmentDAO = new DevelopmentDAO();
            ArrayList<String> paths = new ArrayList<>();
            paths.add("path1");
            paths.add("path2");
            paths.add("path3");
            DevelopmentDTO developmentDTO = new DevelopmentDTO(datecreated1, 33, "SOLUCIONADO3", paths);
            developmentDAO.addDevelopment(123456789L, developmentDTO);
            
        }catch(ControlException ce){
            System.out.print(ce+" "+ce.getType());
        }
        
    
       try{
            Connection.connect();
            String date1 = "2019-06-04";
            LocalDate datecreated1 = LocalDate.parse(date1);
            Long asanaid=44444444444444L;
            UserDTO userDTO = new UserDTO("Francisco",null,"123456789",asanaid);
            TaskDTO taskDTO = new TaskDTO(10620190838L,"Tarea desde JAVA",Task.Type.SECTION,userDTO,1,datecreated1,datecreated1,datecreated1);
            TaskDTO subtaskDTO = new TaskDTO(11111111111L,"Subtarea desde JAVA",Task.Type.SUBTASK,userDTO,1,datecreated1,datecreated1,datecreated1);
            TaskDTO subtask2DTO = new TaskDTO(222222222222L,"Subtarea2 desde JAVA",Task.Type.SUBTASK,userDTO,2,datecreated1,datecreated1,datecreated1);
            TaskDTO taskDTO1 = new TaskDTO(8559666445L,"Subsubtarea Java",Task.Type.SUBTASK,userDTO,1,datecreated1,datecreated1,datecreated1);
            subtask2DTO.addSubtask(taskDTO1);
            taskDTO.addSubtask(subtaskDTO);
            taskDTO.addSubtask(subtask2DTO);
            TaskDAO taskDAO = new TaskDAO();
            taskDAO.addTask(1, taskDTO);*/
            
            
            
            
            /*String date1 = "2019-06-04";
            LocalDate datecreated1 = LocalDate.parse(date1);
            Long asanaid=1117095154678944L;
            UserDTO userDTO = new UserDTO("Fernando","fujh@gmail.com","123456789",asanaid); 
            TaskDTO taskDTO = new TaskDTO(10620190838L,"Tarea desde JAVA",Task.Type.SINGLE,userDTO,1,datecreated1,datecreated1,datecreated1);
            TaskDAO taskDAO = new TaskDAO();
            taskDAO.addTask(1, taskDTO);
            taskDAO.getSubtasks(98657423L);*/
            
            /*TaskDAO taskDAO = new TaskDAO();
            Task task = taskDAO.getTask(56897845);
            System.out.print(task.getName()+" "+task.getType());*/
            
            /*EvidenceDAO evidenceDAO = new EvidenceDAO();
            String date1 = "2019-06-04";
            LocalDate datecreated1 = LocalDate.parse(date1);
            Long asanaid=Long.valueOf(1117095154678944L);
            UserDTO userDTO = new UserDTO("Fernando","fujh@gmail.com","123456789",asanaid); 
            TaskDTO taskDTO = new TaskDTO(10620190838L,"Tarea desde JAVA",Task.Type.SINGLE,userDTO,1,datecreated1,datecreated1,datecreated1);
            TaskDAO taskDAO = new TaskDAO();
            taskDAO.addTask(0, taskDTO);*/
            /*evidenceDAO.addEvidence(1, "evidencia 2 development 1");
            Evidence evidence = evidenceDAO.getEvidence(2);
            System.out.print("EVIDENCE"+evidence.getFilename());
            evidenceDAO.getEvidences(3);
            */
            
            
            
            
            /*DevelopmentDAO developmentDAO = new DevelopmentDAO();
            String date1 = "2019-06-04";
            LocalDate datecreated1 = LocalDate.parse(date1);
            developmentDAO.getDevelopments(56897845L,datecreated1,datecreated1);*/
            /*DevelopmentDAO developmentDAO = new DevelopmentDAO();
            Development development = developmentDAO.getDevelopment(1);
            System.out.print("DEVELOPMENT: "+development.getId());*/
            /*LocalDate date = LocalDate.now();
            ArrayList<String> filepaths = new ArrayList<>();
            DevelopmentDTO developmentDTO = new DevelopmentDTO(date,33,"hola",filepaths);
            
           
           
           
            /*Crear un proyecto nuevo
            ProjectDAO projectDAO = new ProjectDAO();
            ProjectDTO projectDTO = new ProjectDTO("PROYECTO EIFFEL LENGUAJES",16);
            projectDAO.addProject(projectDTO);
            Obtener un proyecto dado su id
            Project project = projectDAO.getProject(2);
            System.out.print(project.getId()+" "+project.getName()+" "+project.getCreated());
            Obtener los proyectos de un administrador
            projectDAO.getAdminProjects(4);
            Obtener los proyectos de un colaborador
             projectDAO.getCollabProjects(17)
            Banear un usuario;
            projectDAO.banUser(1, 17);
            projectDAO.unbanUser(1, 17);*/
            
            
            
            
            
            /*UserDAO usuarioDAO = new UserDAO();
            User user = usuarioDAO.login("carlos2@gmail.com", "12456");
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());
            usuarioDAO.getBannedUsers(1);
            usuarioDAO.getActiveUsers(1);
            Obtener el administrador de un proyecto con el projectid 
            User user = usuarioDAO.getAdministrator(1);
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());
            Obtener usuario asignado a una tarea con el IDtask
            User user = usuarioDAO.getAsignee(145789623325L);
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());
            Obtener un usuario con el IDcollaborator
            User user = usuarioDAO.getUser(22);
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());
            Registrar un usuario
            Long asanaid=Long.valueOf(780190416L);
            usuarioDTO = new UserDTO("Carlos","juan@gmail.com","123456789",asanaid);
            usuarioDAO.registerUser(usuarioDTO);
            
        }catch(ControlException ce){
            System.out.print(ce+" "+ce.getType());
        }
    }*/

    }
}
