package control.dtos;

import java.time.LocalDate;

public class DevelopmentFilter {
    public static final DevelopmentFilter EMPTY = new DevelopmentFilter(null, null);
    
    private LocalDate start;
    private LocalDate end;

    public DevelopmentFilter(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    
    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
