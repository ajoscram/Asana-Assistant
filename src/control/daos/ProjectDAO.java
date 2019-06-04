package control.daos;

import control.ControlException;
import control.daos.connection.Connection;
import control.dtos.ProjectDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import model.User;

public class ProjectDAO {

    public ProjectDAO(){}
    
    public void addProject(ProjectDTO project) throws ControlException{
        try{
            String name = project.getName();
            Long idadministrador = project.getAdministratorId();
            if(name.isEmpty()){
                name = null;
            }
            if(idadministrador==0){
                idadministrador = null;
            }
            
            Connection.getInstance().query("EXEC USP_ADDPROJECT '"+name+"',"+idadministrador);
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
                case 70005:
                    throw new ControlException(ControlException.Type.INVALID_EMAIL_FORMAT,errorMessage);
                case 70006:
                    throw new ControlException(ControlException.Type.FUNTIONALITY_NON_IMPLEMENTED,errorMessage);
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
            //System.out.print(listaProyectos.get(0).getName());
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
            //System.out.print(listaProyectos.get(1).getName());
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
            ResultSet rs;
            if(projectId==0 && userId==0){
                String pId= null;
                String uId= null;
                rs = Connection.getInstance().query("EXEC USP_BANUSER "+pId+","+uId);
            }
            if(projectId==0 && userId!=0){
                String pId= null;
                rs = Connection.getInstance().query("EXEC USP_BANUSER "+pId+","+userId);
            }
            if(projectId!=0 && userId==0){
                String uId= null;
                rs = Connection.getInstance().query("EXEC USP_BANUSER "+projectId+","+uId);
            }
            if(projectId!=0 && userId!=0){
                rs = Connection.getInstance().query("EXEC USP_BANUSER "+projectId+","+userId);
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
                case 70005:
                    throw new ControlException(ControlException.Type.INVALID_EMAIL_FORMAT,errorMessage);
                case 70006:
                    throw new ControlException(ControlException.Type.FUNTIONALITY_NON_IMPLEMENTED,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public void unbanUser(long projectId, long userId) throws ControlException{
        try{
            ResultSet rs;
            if(projectId==0 && userId==0){
                String pId= null;
                String uId= null;
                rs = Connection.getInstance().query("EXEC USP_UNBANUSER "+pId+","+uId);
            }
            if(projectId==0 && userId!=0){
                String pId= null;
                rs = Connection.getInstance().query("EXEC USP_UNBANUSER "+pId+","+userId);
            }
            if(projectId!=0 && userId==0){
                String uId= null;
                rs = Connection.getInstance().query("EXEC USP_UNBANUSER "+projectId+","+uId);
            }
            if(projectId!=0 && userId!=0){
                rs = Connection.getInstance().query("EXEC USP_UNBANUSER "+projectId+","+userId);
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
                case 70005:
                    throw new ControlException(ControlException.Type.INVALID_EMAIL_FORMAT,errorMessage);
                case 70006:
                    throw new ControlException(ControlException.Type.FUNTIONALITY_NON_IMPLEMENTED,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
}
