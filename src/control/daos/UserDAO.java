package control.daos;

import control.dtos.UserDTO;
import java.util.List;
import model.User;

public class UserDAO {

    public UserDAO(){}
    
    public User login(String email, String password){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void addUser(UserDTO user){
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
