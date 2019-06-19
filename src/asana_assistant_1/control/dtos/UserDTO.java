package asana_assistant_1.control.dtos;

public class UserDTO {
    private String name;
    private String email;
    private String password;
    private Long asanaId;
    
    public UserDTO(String name, String email, String password, Long asanaId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.asanaId = asanaId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getAsanaId() {
        return asanaId;
    }
}