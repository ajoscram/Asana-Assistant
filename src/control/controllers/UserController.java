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
    
    public List<DisplayString> getAdminProjectStrings(long id){
        List<Project> projects = dao.getAdminProjects(id);
        List<DisplayString> strings = new ArrayList();
        for(Project project : projects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }
    
    public List<DisplayString> getCollabProjectStrings(long id){
        List<Project> projects = dao.getCollabProjects(id);
        List<DisplayString> strings = new ArrayList();
        for(Project project : projects)
            strings.add(new DisplayString(project.getId(), project.getName()));
        return strings;
    }
}
