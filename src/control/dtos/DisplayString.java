package control.dtos;

public class DisplayString {
    
    private long id;
    private String string;
    
    public DisplayString(long id, String string){
        this.id = id;
        this.string = string;
    }
    
    public long getId(){
        return id;
    }
    
    public void setString(String string){
        this.string = string;
    }
    
    @Override
    public String toString(){
        return string;
    }
}
