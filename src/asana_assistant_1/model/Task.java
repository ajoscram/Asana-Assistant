package asana_assistant_1.model;

import java.time.LocalDate;

public class Task {
    public enum Type { SECTION, SINGLE, SUBTASK }
    
    private long id;
    private String name;
    private LocalDate created;
    private LocalDate due;
    private LocalDate completed;
    private Type type;

    public Task(long id, String name, LocalDate created, LocalDate due, LocalDate completed, Type type) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.due = due;
        this.completed = completed;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getDue() {
        return due;
    }

    public LocalDate getCompleted() {
        return completed;
    }

    public Type getType() {
        return type;
    }
}
