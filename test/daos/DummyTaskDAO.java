package daos;

import control.daos.TaskDAO;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class DummyTaskDAO extends TaskDAO{
    private Task t0 = new Task(0, "Mantenimiento", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 30), null, Task.Type.SECTION);
    private Task t1 = new Task(1, "Limpiar Hojas", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 31), null, Task.Type.SINGLE);
    private Task t2 = new Task(2, "Regar Plantas", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 30), null, Task.Type.SINGLE);
    private Task t3 = new Task(3, "Sacar Manguera", LocalDate.of(2019, Month.MAY, 27), LocalDate.of(2019, Month.MAY, 27), null, Task.Type.SUBTASK);
    private Task t4 = new Task(4, "Conectar Manguera", LocalDate.of(2019, Month.MAY, 28), LocalDate.of(2019, Month.MAY, 28), null, Task.Type.SUBTASK);
    private Task t5 = new Task(5, "Abrir Tubo", LocalDate.of(2019, Month.MAY, 28), LocalDate.of(2019, Month.MAY, 29), null, Task.Type.SUBTASK);
    private Task t6 = new Task(6, "Cerrar Tubo", LocalDate.of(2019, Month.MAY, 28), LocalDate.of(2019, Month.MAY, 30), null, Task.Type.SUBTASK);

    @Override
    public Task getTask(long id) {
        if(id == 0)
            return t0;
        else if(id == 1)
            return t1;
        else if(id == 2)
            return t2;
        else if(id == 3)
            return t3;
        else if(id == 4)
            return t4;
        else if(id == 5)
            return t5;
        else if(id == 6)
            return t6;
        else
            return super.getTask(id);
    }
    
    @Override
    public List<Task> getSubtasks(long id) {
        ArrayList<Task> tasks = new ArrayList();
        if(id == 0)
            return tasks;
        else if(id == 1)
            return tasks;
        else if(id == 2){
            tasks.add(t3);
            tasks.add(t4);
            tasks.add(t5);
            tasks.add(t6);
            return tasks;
        }
        else if(id == 3)
            return tasks;
        else if(id == 4)
            return tasks;
        else if(id == 5)
            return tasks;
        else if(id == 6)
            return tasks;
        else
            return super.getSubtasks(id);
    }

    @Override
    public List<Task> getTasks(long projectId) {
        if(projectId == 0){
            ArrayList<Task> tasks = new ArrayList();
            tasks.add(t0);
            tasks.add(t1);
            tasks.add(t2);
            return tasks;
        }
        else
            return super.getTasks(projectId);
    }
}
