package model;

import java.time.LocalDate;

public class Development {
    private long id;
    private LocalDate date;
    private int hours;
    private String description;
    private LocalDate created;
    

    public Development(long id, LocalDate date, int hours, String description, LocalDate created) {
        this.id = id;
        this.date = date;
        this.hours = hours;
        this.description = description;
        this.created = created;
    }

    public long getId() {
        return id;
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

    public LocalDate getCreated() {
        return created;
    }
}
