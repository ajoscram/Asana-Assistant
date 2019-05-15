package control.controllers;

import control.dtos.DisplayString;
import control.dtos.UserDTO;
import java.util.List;
import model.User;

public class UserController {
    
    public UserController() {}
    
    public User login(String email, String password){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void addUser(UserDTO dto){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public User getUser(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getAdminProjectStrings(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getCollabProjectStrings(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
