package asana_assistant_1.control.daos;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.daos.connection.Connection;
import asana_assistant_1.control.dtos.TaskDTO;
import asana_assistant_1.control.dtos.UserDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.model.Task;

public class TaskDAO {
    
    private String getDateString(LocalDate date){
        if(date != null)
            return "'" + date + "'";
        else
            return null;
    }
    
    private void addSubtask(long idProject,long idFatherTask, TaskDTO subtask) throws ControlException{
        try{
            Long idtask = subtask.getId();
            String name = subtask.getName();
            String created = getDateString(subtask.getCreated());
            String completed = getDateString(subtask.getCompleted());
            String dueto = getDateString(subtask.getDue());
            if(subtask.getAsignee()!= null){
                addUserDTO(subtask.getAsignee());
                long idcollaborator = getIDcollaborator(subtask.getAsignee());
                Connection.getInstance().queryinsert("EXEC USP_ADDSUBTASK "+idtask+","+idFatherTask+","+idcollaborator+","+idProject+",'"+name+"',"+created+","+dueto+","+completed+","+subtask.getIndex());
            }else{
                Connection.getInstance().queryinsert("EXEC USP_ADDSUBTASK "+idtask+","+idFatherTask+","+null+","+idProject+",'"+name+"',"+created+","+dueto+","+completed+","+subtask.getIndex());
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
            String created = getDateString(dto.getCreated());
            String completed = getDateString(dto.getCompleted());
            String dueto = getDateString(dto.getDue());
            String typetask = dto.getType().toString();
            if(dto.getAsignee()!=null){
                addUserDTO(dto.getAsignee());
                Long idcollaborator = getIDcollaborator(dto.getAsignee());
                Connection.getInstance().queryinsert("EXEC USP_ADDTASK "+idproject+","+idtask+","+idcollaborator+",'"+name+"',"+created+","+dueto+","+completed+",'"+typetask+"'");
            }else{
                Connection.getInstance().queryinsert("EXEC USP_ADDTASK "+idproject+","+idtask+","+null+",'"+name+"',"+created+","+dueto+","+completed+",'"+typetask+"'");
            }
            
            for(TaskDTO subtask : dto.getSubtasks()){
                addSubtask(idproject,dto.getId(), subtask);
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
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
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
