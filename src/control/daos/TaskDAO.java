package control.daos;

import control.ControlException;
import control.daos.connection.Connection;
import control.dtos.TaskDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Task;

public class TaskDAO {
    
    public TaskDAO(){}
    
    public void addTask(long idproject, TaskDTO dto) throws ControlException{
        try{
            ResultSet rs;
            Long idtask = dto.getId();
            
            Long idcollaborator = dto.getAsignee().getAsanaId();
            String name = dto.getName();
            if(name.isEmpty()){
                name = null;
            }
            if(idproject==0 && idtask==0){
                String pid = null;
                String tid =null;
                rs = Connection.getInstance().query("EXEC USP_ADDTASK '"+pid+"','"+tid+"','"+idcollaborator+"','"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType()+"'");
            }else if(idproject !=0 && idtask==0){
                String tid =null;
                rs = Connection.getInstance().query("EXEC USP_ADDTASK '"+idproject+"','"+tid+"','"+idcollaborator+"','"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType()+"'");
            }else if(idproject ==0 && idtask!=0){
                String pid = null;
                rs = Connection.getInstance().query("EXEC USP_ADDTASK '"+pid+"','"+idtask+"','"+idcollaborator+"','"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType()+"'");
            }else{
                rs = Connection.getInstance().query("EXEC USP_ADDTASK '"+idproject+"','"+idtask+"','"+idcollaborator+"','"+name+"','"+dto.getCreated()+"','"+dto.getDue()+"','"+dto.getCompleted()+"','"+dto.getType()+"'");
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
    
    public Task getTask(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Task> getTasks(long projectId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Task> getTasks(long projectId, long asigneeId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Task> getSubtasks(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Task> getSubtasks(long id, long asigneeId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
