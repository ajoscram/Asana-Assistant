package control.dtos;

import java.time.LocalDate;

public class Filter {
    private Long taskId;
    private Long asigneeId;
    private LocalDate start;
    private LocalDate end;

    public Filter(Long taskId, Long asigneeId, LocalDate start, LocalDate end) {
        this.taskId = taskId;
        this.asigneeId = asigneeId;
        this.start = start;
        this.end = end;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getAsigneeId() {
        return asigneeId;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
