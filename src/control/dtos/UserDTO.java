package control.dtos;

public class UserDTO {
    private String name;
    private String email;
    private String password;
    private long asanaId;

    public UserDTO(String name, String email, String password, long asanaId) {
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

    public long getAsanaId() {
        return asanaId;
    }
}