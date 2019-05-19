package control.daos;

import control.dtos.UserDTO;
import java.util.List;
import model.Project;
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
    
    public List<Project> getAdminProjects(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Project> getCollabProjects(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
