package control.daos;

import control.ControlException;
import control.daos.connection.Connection;
import control.dtos.TaskDTO;
import control.dtos.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskDAO {
    
    private void addSubtask(long idProject,long idFatherTask, TaskDTO subtask) throws ControlException{
        try{
            Long idtask = subtask.getId();
            System.out.println("Long idtask = subtask.getId();");
            String name = subtask.getName();
            if(subtask.getAsignee()!= null){
                addUserDTO(subtask.getAsignee());
                System.out.println("addUserDTO(subtask.getAsignee());");
                long idcollaborator = getIDcollaborator(subtask.getAsignee());
                System.out.println("long idcollaborator = getIDcollaborator(subtask.getAsignee());");
                Connection.getInstance().query("EXEC USP_ADDSUBTASK "+idtask+","+idFatherTask+","+idcollaborator+","+idProject+",'"+name+"','"+subtask.getCreated()+"','"+subtask.getDue()+"','"+subtask.getCompleted()+"','"+subtask.getType().toString()+"'");
            }else{
                Connection.getInstance().query("EXEC USP_ADDSUBTASK "+idtask+","+idFatherTask+","+null+","+idProject+",'"+name+"','"+subtask.getCreated()+"','"+subtask.getDue()+"','"+subtask.getCompleted()+"','"+subtask.getType().toString()+"'");
            }
            for(TaskDTO subsubtask : subtask.getSubtasks())
                addSubtask(idProject,subtask.getId(), subsubtask);
            
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
                case 70003:
                    throw new ControlException(ControlException.Type.DUPLICATE_VALUE,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    private void addUserDTO(UserDTO userDTO) throws ControlException{
        if(userDTO!=null){
            new UserDAO().addUser(userDTO);
        }
    }
    private long getIDcollaborator(UserDTO userDTO) throws SQLException,ControlException{
        if(userDTO.getAsanaId() != null){
            ResultSet rs = Connection.getInstance().query("EXEC USP_GETIDCOLLABORATOR "+userDTO.getAsanaId());
            if(rs.next()){
                return rs.getLong("IDcollaborator");
            }
            throw new ControlException(ControlException.Type.UNREACHABLE_STATEMENT_ERROR,"TaskDAO.getIDcollaborator()");
        }else if(userDTO.getEmail() != null){
            ResultSet rs = Connection.getInstance().query("EXEC USP_GETIDCOLLABORATOR "+userDTO.getEmail());
            return rs.getLong("IDcollaborator");
        }else{
            throw new ControlException(ControlException.Type.UNREACHABLE_STATEMENT_ERROR,"TaskDAO.getIDcollaborator()");
        }
    }
    public void addTask(long idproject, TaskDTO dto) throws ControlException{
        try{
            Long idtask = dto.getId();
            String name = dto.getName();
            if(dto.getAsignee()!=null){
                addUserDTO(dto.getAsignee());
                Long idcollaborator = getIDcollaborator(dto.getAsignee());
                LocalDate created = dto.getCreated();
                LocalDate completed = dto.getCompleted();
                LocalDate dueto = dto.getDue();
                String typetask = dto.getType().toString();
                System.out.println("EXEC USP_ADDTASK "+idproject+","+idtask+","+idcollaborator+",'"+name+"','"+created+"','"+dueto+"','"+completed+"','"+typetask+"'");
                ResultSet result = Connection.getInstance().query("EXEC USP_ADDTASK "+idproject+","+idtask+","+idcollaborator+",'"+name+"','"+created+"','"+dueto+"','"+completed+"','"+typetask+"'");
                
            }else{
                //ResultSet result = Connection.getInstance().query("EXEC USP_ADDTASK "+idproject+","+idtask+","+null+",'"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType().toString()+"'");
            }
            
            for(TaskDTO subtask : dto.getSubtasks()){
                addSubtask(idproject,dto.getId(), subtask);
                System.out.println("addSubtask(idproject,dto.getId(), subtask);");
            }
            
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70001:
                    return;
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                case 70003:
                    throw new ControlException(ControlException.Type.DUPLICATE_VALUE,errorMessage);
                default:
                    System.out.println("ERRORCODE: "+ex.getErrorCode());
                    System.out.println("STATE: "+ex.getSQLState());
                    System.out.println("STACKTRACE: "+ex.getLocalizedMessage());
            }
        }
    }
    
    public Task getTask(long id) throws ControlException{
        try{
            ResultSet rs;
            if(id==0){
                String ids = null;
                 rs = Connection.getInstance().query("EXEC USP_GETTASK "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETTASK "+id);
            }
            if(rs.next()==true){
                LocalDate datecreated = null;
                LocalDate datedueto = null;
                LocalDate datecompleted = null;
                
                Long IDtask = rs.getLong("IDtask");
                String name = rs.getString("name");
                String created = rs.getString("datecreated");
                String dueto = rs.getString("dueto");
                
                String completed = rs.getString("completed");
                if(created!=null){
                    datecreated = LocalDate.parse(created);
                }
                if(dueto!=null){
                    datedueto = LocalDate.parse(dueto);
                }
                if(completed!=null){
                    datecompleted = LocalDate.parse(completed);
                }
                String type = rs.getString("typetask");
                return new Task(IDtask,name,datecreated,datedueto,datecompleted,Task.Type.valueOf(type));
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
    
    public List<Task> getTasks(long projectId) throws ControlException{
        try{
            ResultSet rs;
            if(projectId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETTASKS "+ids+",null");
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETTASKS "+projectId+",null");
            }
            ArrayList<Task> listaTareas = new ArrayList<>();
            
            while(rs.next()){
                //IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask
                LocalDate datecreated = null;
                LocalDate datedueto = null;
                LocalDate datecompleted = null;
                
                Long IDtask = rs.getLong("IDtask");
                String name = rs.getString("name");
                
                String created = rs.getString("datecreated");
                String dueto = rs.getString("dueto");
                String completed = rs.getString("completed");
                String type = rs.getString("typetask");
                
                if(created!=null){
                    datecreated = LocalDate.parse(created);
                }
                if(dueto!=null){
                    datedueto = LocalDate.parse(dueto);
                }
                if(completed!=null){
                    datecompleted = LocalDate.parse(completed);
                }
                Task task = new Task(IDtask,name,datecreated,datedueto,datecompleted,Task.Type.valueOf(type));
                listaTareas.add(task);
                    
            }
            //System.out.print(listaTareas.get(0).getName());
            return listaTareas;
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
    
    public List<Task> getTasks(long projectId, long asigneeId) throws ControlException{
        try{
            ResultSet rs;
            if(projectId==0 || asigneeId==0){
                String pid = null;
                String aid = null;
                rs = Connection.getInstance().query("EXEC USP_GETTASKS "+pid+","+aid);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETTASKS "+projectId+","+asigneeId);
            }
            ArrayList<Task> listaTareas = new ArrayList<>();
            
            while(rs.next()){
                //IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask
                LocalDate datecreated = null;
                LocalDate datedueto = null;
                LocalDate datecompleted = null;
                
                Long IDtask = rs.getLong("IDtask");
                String name = rs.getString("name");
                
                String created = rs.getString("datecreated");
                String dueto = rs.getString("dueto");
                String completed = rs.getString("completed");
                String type = rs.getString("typetask");
                
                if(created!=null){
                    datecreated = LocalDate.parse(created);
                }
                if(dueto!=null){
                    datedueto = LocalDate.parse(dueto);
                }
                if(completed!=null){
                    datecompleted = LocalDate.parse(completed);
                }
                Task task = new Task(IDtask,name,datecreated,datedueto,datecompleted,Task.Type.valueOf(type));
                listaTareas.add(task);
                    
            }
            //System.out.print(listaTareas.get(0).getName());
            return listaTareas;
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
    
    public List<Task> getSubtasks(long id) throws ControlException{
        try{
            ResultSet rs;
            if(id==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETSUBTASKS "+ids+",null");
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETSUBTASKS "+id+",null");
            }
            ArrayList<Task> listaTareas = new ArrayList<>();
            
            while(rs.next()){
                //IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask
                LocalDate datecreated = null;
                LocalDate datedueto = null;
                LocalDate datecompleted = null;
                
                Long IDtask = rs.getLong("IDtask");
                String name = rs.getString("name");
                
                String created = rs.getString("datecreated");
                String dueto = rs.getString("dueto");
                String completed = rs.getString("completed");
                String type = rs.getString("typetask");
                
                if(created!=null){
                    datecreated = LocalDate.parse(created);
                }
                if(dueto!=null){
                    datedueto = LocalDate.parse(dueto);
                }
                if(completed!=null){
                    datecompleted = LocalDate.parse(completed);
                }
                Task task = new Task(IDtask,name,datecreated,datedueto,datecompleted,Task.Type.valueOf(type));
                listaTareas.add(task);
                    
            }
            System.out.print(listaTareas.get(0).getName());
            return listaTareas;
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
    
    public List<Task> getSubtasks(long id, long asigneeId) throws ControlException{
        try{
            ResultSet rs;
            if(id==0 || asigneeId==0){
                String pid = null;
                String aid = null;
                rs = Connection.getInstance().query("EXEC USP_GETSUBTASKS "+pid+","+aid);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETSUBTASKS "+id+","+asigneeId);
            }
            ArrayList<Task> listaTareas = new ArrayList<>();
            
            while(rs.next()){
                //IDcollaborator,TASK.IDtask,name,datecreated,dueto,completed,typetask
                LocalDate datecreated = null;
                LocalDate datedueto = null;
                LocalDate datecompleted = null;
                
                Long IDtask = rs.getLong("IDtask");
                String name = rs.getString("name");
                
                String created = rs.getString("datecreated");
                String dueto = rs.getString("dueto");
                String completed = rs.getString("completed");
                String type = rs.getString("typetask");
                
                if(created!=null){
                    datecreated = LocalDate.parse(created);
                }
                if(dueto!=null){
                    datedueto = LocalDate.parse(dueto);
                }
                if(completed!=null){
                    datecompleted = LocalDate.parse(completed);
                }
                Task task = new Task(IDtask,name,datecreated,datedueto,datecompleted,Task.Type.valueOf(type));
                listaTareas.add(task);
                    
            }
            System.out.print(listaTareas.get(0).getName());
            return listaTareas;
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
}
