package asana_assistant_1.control.dtos;

public class ProjectDTO {
    private String name;
    private long administratorId;

    public ProjectDTO(String name, long administratorId) {
        this.name = name;
        this.administratorId = administratorId;
    }

    public String getName() {
        return name;
    }

    public long getAdministratorId() {
        return administratorId;
    }
}
