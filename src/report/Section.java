package report;

public abstract class Section {
    private String name;
    
    public Section(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
}
