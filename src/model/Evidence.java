package model;

public class Evidence {
    private long id;
    private String diskname;
    private String filename;
    
    public Evidence(long id, String diskname, String filename) {
        this.id = id;
        this.filename = filename;
        this.diskname = diskname;
    }

    public long getId() {
        return id;
    }

    public String getDiskname(){
        return diskname;
    }
    
    public String getFilename() {
        return filename;
    }
}
