package asana_assistant_1.control.daos;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.daos.connection.Connection;
import asana_assistant_1.control.dtos.ProjectDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.model.Project;

public class ProjectDAO {

    public ProjectDAO(){}
    
    public void addProject(ProjectDTO project) throws ControlException{
        try{
            String name = project.getName();
            Long idadministrador = project.getAdministratorId();
            if(name.isEmpty()){
                name = null;
            }
            Connection.getInstance().queryinsert("EXEC USP_ADDPROJECT '"+name+"',"+idadministrador);
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70001:
                    throw new ControlException(ControlException.Type.DUPLICATE_VALUE,errorMessage);
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public Project getProject(long id) throws ControlException{
        try{
            ResultSet rs;
            if(id==0){
                String ids = null;
                 rs = Connection.getInstance().query("EXEC USP_GETPROJECT "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETPROJECT "+id);
            }
            if(rs.next()==true){
                Long IDproject = rs.getLong("IDproject");
                String name = rs.getString("name");
                String date = rs.getString("datecreated");
                LocalDate datecreated = LocalDate.parse(date);
                return new Project(IDproject,name,datecreated);
            }else{
                throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,"Error: User not found");
            }
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public List<Project> getAdminProjects(long userId) throws ControlException{
        try{
            ResultSet rs;
            if(userId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETADMINPROJECTS "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETADMINPROJECTS "+userId);
            }
            ArrayList<Project> listaProyectos = new ArrayList<>();
            
            while(rs.next()){
                Long IDproject = rs.getLong("IDproject");
                String name = rs.getString("name");
                String date = rs.getString("datecreated");
                LocalDate datecreated = LocalDate.parse(date);
                Project project = new Project(IDproject,name,datecreated);
                listaProyectos.add(project);
                    
            }
            return listaProyectos;
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    public List<Project> getCollabProjects(long userId) throws ControlException{
        try{
            ResultSet rs;
            if(userId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETCOLLABPROJECTS "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETCOLLABPROJECTS "+userId);
            }
            ArrayList<Project> listaProyectos = new ArrayList<>();
            
            while(rs.next()){
                Long IDproject = rs.getLong("IDproject");
                String name = rs.getString("name");
                String date = rs.getString("datecreated");
                LocalDate datecreated = LocalDate.parse(date);
                Project project = new Project(IDproject,name,datecreated);
                listaProyectos.add(project);
                    
            }
            return listaProyectos;
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public void banUser(long projectId, long userId) throws ControlException{
        try{
            if(projectId==0 && userId==0){
                String pId= null;
                String uId= null;
                Connection.getInstance().queryinsert("EXEC USP_BANUSER "+pId+","+uId);
            }
            if(projectId==0 && userId!=0){
                String pId= null;
                Connection.getInstance().queryinsert("EXEC USP_BANUSER "+pId+","+userId);
            }
            if(projectId!=0 && userId==0){
                String uId= null;
                Connection.getInstance().queryinsert("EXEC USP_BANUSER "+projectId+","+uId);
            }
            if(projectId!=0 && userId!=0){
                Connection.getInstance().queryinsert("EXEC USP_BANUSER "+projectId+","+userId);
            }
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
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public void unbanUser(long projectId, long userId) throws ControlException{
        try{
            if(projectId==0 && userId==0){
                String pId= null;
                String uId= null;
                Connection.getInstance().queryinsert("EXEC USP_UNBANUSER "+pId+","+uId);
            }
            if(projectId==0 && userId!=0){
                String pId= null;
                Connection.getInstance().queryinsert("EXEC USP_UNBANUSER "+pId+","+userId);
            }
            if(projectId!=0 && userId==0){
                String uId= null;
                Connection.getInstance().queryinsert("EXEC USP_UNBANUSER "+projectId+","+uId);
            }
            if(projectId!=0 && userId!=0){
                Connection.getInstance().queryinsert("EXEC USP_UNBANUSER "+projectId+","+userId);
            }
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
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
}
