package asana_assistant_1.model;

import java.time.LocalDate;

public class Project {
    private long id;
    private String name;
    private LocalDate created;

    public Project(long id, String name, LocalDate created) {
        this.id = id;
        this.name = name;
        this.created = created;
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
}
