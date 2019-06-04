package gabriel;

import control.daos.connection.Connection;
import control.ControlException;
import control.daos.ProjectDAO;
import control.daos.UserDAO;
import control.dtos.ProjectDTO;
import control.dtos.UserDTO;
import model.User;
import model.Project;



public class GabrielMain {

    public static void main(String[] args) {
        try{
            Connection.connect();
            ProjectDAO projectDAO = new ProjectDAO();
            projectDAO.unbanUser(1, 17);
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
            projectDAO.banUser(1, 17);*/
            
            
            
            
            
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
            usuarioDAO.registerUser(usuarioDTO);*/
            
        }catch(ControlException ce){
            System.out.print(ce);
        }
    }

}
