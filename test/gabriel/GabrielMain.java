package gabriel;

import control.daos.connection.Connection;
import control.ControlException;
import control.daos.UserDAO;
import control.dtos.UserDTO;



public class GabrielMain {

    public static void main(String[] args) {
        try{
            Connection.connect();
            UserDAO usuarioDAO = new UserDAO();
            UserDTO usuarioDTO;
            Long asanaid=Long.valueOf(660190216);
            usuarioDTO = new UserDTO("Carlos","carlos@gmail.com","123456789",asanaid);
            usuarioDAO.registerUser(usuarioDTO);
        }catch(ControlException ce){
            System.out.print(ce);
        }
    }

}
