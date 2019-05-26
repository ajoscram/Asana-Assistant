package control.daos;

import control.dtos.ProjectDTO;
import java.util.List;
import model.Project;

public class ProjectDAO {

    public ProjectDAO(){}
    
    public void addProject(ProjectDTO project){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Project getProject(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Project> getAdminProjects(long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Project> getCollabProjects(long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void banUser(long projectId, long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void unbanUser(long projectId, long userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
