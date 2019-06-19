package asana_assistant_1.model;

public class User {
    private long id;
    private long asanaId;
    private String name;
    private String email;
    private boolean registered;

    public User(long id, long asanaId, String name, String email, boolean registered) {
        this.id = id;
        this.asanaId = asanaId;
        this.name = name;
        this.email = email;
        this.registered = registered;
    }

    public long getId() {
        return id;
    }

    public long getAsanaId() {
        return asanaId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isRegistered() {
        return registered;
    }
}
