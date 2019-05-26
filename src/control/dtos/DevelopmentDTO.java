package control.dtos;

import java.time.LocalDate;
import java.util.List;

public class DevelopmentDTO {
    private LocalDate date;
    private int hours;
    private String description;
    private List<String> evidenceFilepaths;

    public DevelopmentDTO(LocalDate date, int hours, String description, List<String> evidenceFilepaths) {
        this.date = date;
        this.hours = hours;
        this.description = description;
        this.evidenceFilepaths = evidenceFilepaths;
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

    public List<String> getEvidenceFilepaths() {
        return evidenceFilepaths;
    }
}
