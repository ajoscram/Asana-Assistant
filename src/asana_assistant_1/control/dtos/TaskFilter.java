package asana_assistant_1.control.dtos;

public class TaskFilter {
    public static final TaskFilter EMPTY = new TaskFilter(null, null);
    
    private Long taskId;
    private Long asigneeId;
    
    public TaskFilter(Long taskId, Long asigneeId) {
        this.taskId = taskId;
        this.asigneeId = asigneeId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getAsigneeId() {
        return asigneeId;
    }
}
