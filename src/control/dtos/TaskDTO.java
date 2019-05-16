package control.dtos;

import control.ControlException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Task;

public class TaskDTO extends Task{

    private ArrayList<Task> subtasks;
    
    public TaskDTO(long id, String name, LocalDate created, LocalDate due, LocalDate completed, Type type) {
        super(id, name, created, due, completed, type);
        this.subtasks = new ArrayList();
    }
    
    public void addSubtask(Task task) throws ControlException {
        if(task.getType() != Task.Type.SUBTASK)
            throw new ControlException(ControlException.Type.SUBTASK_NOT_TYPED_CORRECTLY);
        subtasks.add(task);
    }
}
