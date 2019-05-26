package control.controllers;

import control.daos.UserDAO;
import control.dtos.DisplayString;
import control.dtos.UserDTO;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import model.User;

public class UserController {
    
    private UserDAO dao;
    
    public UserController() {
        dao = new UserDAO();
    }
    
    public User login(String email, String password){
        return dao.login(email, password);
    }
    
    public void addUser(UserDTO dto){
        dao.addUser(dto);
    }
    
    public User getUser(long id){
        return dao.getUser(id);
    }
    
    public DisplayString getAsigneeString(long id){
        User user = dao.getAsignee(id);
        return new DisplayString(user.getId(), user.getName());
    }
    
    public DisplayString getAdministratorString(long projectId) {
        User administrator = dao.getAdministrator(projectId);
        return new DisplayString(administrator.getId(), administrator.getName());
    }
    
    public List<DisplayString> getActiveUserStrings(long projectId) {
        List<User> users = dao.getActiveUsers(projectId);
        List<DisplayString> strings = new ArrayList();
        String string;
        for(User user : users){
            string = user.getName() + " (" + user.getEmail() + ")";
            strings.add(new DisplayString(user.getId(), string));
        }
        return strings;
    }
    
    public List<DisplayString> getBannedUserStrings(long projectId) {
        List<User> users = dao.getBannedUsers(projectId);
        List<DisplayString> strings = new ArrayList();
        String string;
        for(User user : users){
            string = user.getName() + " (" + user.getEmail() + ")";
            strings.add(new DisplayString(user.getId(), string));
        }
        return strings;
    }
}
