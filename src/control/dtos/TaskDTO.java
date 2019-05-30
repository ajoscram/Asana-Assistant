package control.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskDTO extends Task {

    private ArrayList<TaskDTO> subtasks;
    private UserDTO asignee;
    private int index;
    
    public TaskDTO(long id, String name, Type type, UserDTO asignee, int index, LocalDate created, LocalDate due, LocalDate completed) {
        super(id, name, created, due, completed, type);
        this.subtasks = new ArrayList();
        this.asignee = asignee;
        this.index = index;
    }
    
    public void addSubtask(TaskDTO task) {
        subtasks.add(task);
    }
    
    public List<TaskDTO> getSubtasks(){
        return subtasks;
    }
    
    public UserDTO getAsignee(){
        return asignee;
    }
    
    public int getIndex(){
        return index;
    }
    
    @Override
    public String toString(){
        return this.getName();
    }
}
