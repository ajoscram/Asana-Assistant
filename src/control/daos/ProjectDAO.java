package control.daos;

import control.dtos.ProjectDTO;
import control.dtos.TaskDTO;
import control.dtos.TaskFilter;
import java.util.List;
import model.Project;
import model.Task;
import model.User;

public class ProjectDAO {

    public ProjectDAO(){}
    
    public void addProject(ProjectDTO project){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Project getProject(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void banUser(long projectId, long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void unbanUser(long projectId, long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }   
    
    public User getAdministrator(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<User> getActiveUsers(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<User> getBannedUsers(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void addTask(long projectId, TaskDTO dto){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Task> getTasks(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Task> getTasks(long id, TaskFilter filter){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
