package control.controllers;

import control.dtos.DisplayString;
import java.util.List;
import model.Task;

public class TaskController {
    
    public TaskController(){}
    
    public Task getTask(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public DisplayString getAsigneeString(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getSubtaskStrings(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getDevelopmentStrings(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
