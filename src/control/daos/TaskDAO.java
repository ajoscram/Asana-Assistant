package control.daos;

import java.util.List;
import model.Development;
import model.Task;
import model.User;

public class TaskDAO {
    
    public TaskDAO(){}
    
    public Task getTask(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public User getAsignee(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Task> getSubtasks(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Development> getDevelopments(long taskId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
