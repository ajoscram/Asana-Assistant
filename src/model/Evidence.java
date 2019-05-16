package model;

public class Evidence {
    private long id;
    private String filename;

    public Evidence(long id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }
}
