package control.daos;

import control.ControlException;
import control.dtos.UserDTO;
import java.util.List;
import model.User;
import control.daos.connection.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    public UserDAO(){}
    
    public User login(String email, String password){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void registerUser(UserDTO user) throws ControlException{
        try{
            String name = user.getName();
            String password = user.getPassword();
            String email = user.getEmail();
            Long id = user.getAsanaId();
            Connection.getInstance().query("EXEC USP_REGISTERUSER '"+name+"','"+email+"',"+id+",'"+password+"'");
            System.out.print("Usuario Agregado Exitosamente");
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70001:
                    throw new ControlException(ControlException.Type.DUPLICATE_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                default:
                    break;
            }
        }
    }
    
    public void addUser(UserDTO user) throws ControlException{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public User getUser(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public User getAsignee(long taskId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public User getAdministrator(long projectId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<User> getActiveUsers(long projectId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<User> getBannedUsers(long projectId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
