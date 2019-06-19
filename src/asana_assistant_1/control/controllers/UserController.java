package asana_assistant_1.control.controllers;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.daos.UserDAO;
import asana_assistant_1.control.dtos.DisplayString;
import asana_assistant_1.control.dtos.UserDTO;
import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.model.Project;
import asana_assistant_1.model.User;

public class UserController {
    
    private UserDAO dao;
    
    public UserController() {
        dao = new UserDAO();
    }
    
    public User login(String email, String password) throws ControlException {
        return dao.login(email, password);
    }
    
    public void registerUser(UserDTO dto) throws ControlException {
        dao.registerUser(dto);
    }
    
    public User getUser(long id) throws ControlException {
        return dao.getUser(id);
    }
    
    public DisplayString getAsigneeString(long id) throws ControlException {
        User user = dao.getAsignee(id);
        if(user != null)
            return new DisplayString(user.getId(), user.getName());
        else
            return null;
    }
    
    public DisplayString getAdministratorString(long projectId) throws ControlException {
        User administrator = dao.getAdministrator(projectId);
        return new DisplayString(administrator.getId(), administrator.getName());
    }
    
    public List<DisplayString> getActiveUserStrings(long projectId) throws ControlException {
        List<User> users = dao.getActiveUsers(projectId);
        List<DisplayString> strings = new ArrayList();
        String string;
        for(User user : users){
            string = user.getName();
            if(user.getEmail() != null)
                string +=" (" + user.getEmail() + ")";
            strings.add(new DisplayString(user.getId(), string));
        }
        return strings;
    }
    
    public List<DisplayString> getBannedUserStrings(long projectId) throws ControlException {
        List<User> users = dao.getBannedUsers(projectId);
        List<DisplayString> strings = new ArrayList();
        String string;
        for(User user : users){
            string = user.getName();
            if(user.getEmail() != null)
                string +=" (" + user.getEmail() + ")";
            strings.add(new DisplayString(user.getId(), string));
        }
        return strings;
    }
}
