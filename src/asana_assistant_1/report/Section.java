package asana_assistant_1.report;

public abstract class Section {
    private String name;
    
    public Section(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        if(name == null)
            return "";
        else
            return name;
    }
}
