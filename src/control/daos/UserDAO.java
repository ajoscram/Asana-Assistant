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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void addUser(UserDTO user) throws ControlException{
        try{
            String name = user.getName();
            String password = user.getPassword();
            String email = user.getEmail();
            Long id = user.getAsanaId();
            ResultSet rs = Connection.getInstance().query("exec usp_createuser '"+name+"', '"+password+"', '"+email+"', "+id);
        } catch(SQLException ex){
            //manejo de errores
        }
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
