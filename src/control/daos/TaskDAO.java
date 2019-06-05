package control.daos;

import control.ControlException;
import control.daos.connection.Connection;
import control.dtos.TaskDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskDAO {
    
    public TaskDAO(){}
    
    private void addSubtask(long idParentTask, TaskDTO subtask){
        
        //
        
        for(TaskDTO subsubtask : subtask.getSubtasks())
            addSubtask(subtask.getId(), subsubtask);
    }
    
    public void addTask(long idproject, TaskDTO dto) throws ControlException{
        try{
            ResultSet rs;
            Long idtask = dto.getId();   
            Long idcollaborator = dto.getId();
                
            String name = dto.getName();
            if(name.isEmpty()){
                name = null;
            }
            if(idproject==0 && idtask==0){
                String pid = null;
                String tid =null;
                rs = Connection.getInstance().query("EXEC USP_ADDTASK "+pid+","+tid+","+idcollaborator+",'"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType().toString()+"'");
            }else if(idproject !=0 && idtask==0){
                String tid =null;
                rs = Connection.getInstance().query("EXEC USP_ADDTASK "+idproject+","+tid+","+idcollaborator+",'"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType().toString()+"'");
            }else if(idproject ==0 && idtask!=0){
                String pid = null;
                rs = Connection.getInstance().query("EXEC USP_ADDTASK "+pid+","+idtask+","+idcollaborator+",'"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType().toString()+"'");
            }else{
                rs = Connection.getInstance().query("EXEC USP_ADDTASK "+idproject+","+idtask+","+idcollaborator+",'"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType().toString()+"'");
                for(TaskDTO subtask : dto.getSubtasks())
                    addSubtask(dto.getId(), subtask);
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
