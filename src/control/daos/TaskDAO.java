package control.daos;

import control.dtos.TaskDTO;
import java.util.List;
import model.Task;

public class TaskDAO {
    
    public TaskDAO(){}
    
    public void addTask(long projectId, TaskDTO dto){
        throw new UnsupportedOperationException("Not supported yet.");
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
