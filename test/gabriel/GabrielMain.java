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
            User user = usuarioDAO.getUser(4L);
            System.out.print(user.getId()+"  "+user.getName()+"  "+user.getEmail()+"  "+user.getAsanaId()+"  "+user.isRegistered());
            /*Registrar un usuario
            Long asanaid=Long.valueOf(780190416);
            usuarioDTO = new UserDTO("Carlos","juan@gmail.com","123456789",asanaid);
            usuarioDAO.registerUser(usuarioDTO);*/
            /*Obtener un usuario dado un id
            usuarioDAO.getUser(4);*/
            /*usuarioDAO.getAsignee(4);*/
        }catch(ControlException ce){
            System.out.print(ce);
        }
    }

}
