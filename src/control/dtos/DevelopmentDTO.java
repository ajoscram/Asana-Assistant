package control.dtos;

import java.time.LocalDate;
import java.util.List;

public class DevelopmentDTO {
    private long taskId;
    private LocalDate date;
    private int hours;
    private String description;
    private List<String> evidenceStrings;

    public DevelopmentDTO(long taskId, LocalDate date, int hours, String description, List<String> evidenceStrings) {
        this.taskId = taskId;
        this.date = date;
        this.hours = hours;
        this.description = description;
        this.evidenceStrings = evidenceStrings;
    }

    public long getTaskId() {
        return taskId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getEvidenceStrings() {
        return evidenceStrings;
    }
}
