package gabriel;

import control.daos.connection.Connection;
import control.ControlException;
import control.daos.UserDAO;
import control.dtos.UserDTO;
import model.User;



public class GabrielMain {

    public static void main(String[] args) {
        try{
            Connection.connect();
            UserDAO usuarioDAO = new UserDAO();
            usuarioDAO.getBannedUsers(1);
            /*usuarioDAO.getActiveUsers(1);*/
            /*Obtener el administrador de un proyecto con el projectid 
            User user = usuarioDAO.getAdministrator(1);
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());*/
            /*Obtener usuario asignado a una tarea con el IDtask
            User user = usuarioDAO.getAsignee(145789623325L);
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());*/
            /*Obtener un usuario con el IDcollaborator
            User user = usuarioDAO.getUser(22);
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());*/
            /*Registrar un usuario
            Long asanaid=Long.valueOf(780190416L);
            usuarioDTO = new UserDTO("Carlos","juan@gmail.com","123456789",asanaid);
            usuarioDAO.registerUser(usuarioDTO);*/
            
        }catch(ControlException ce){
            System.out.print(ce);
        }
    }

}
