package report;

public class ReportException extends Exception {

    public enum Type {  }
    
    private Type type;
    
    public ReportException(Type type) {
        super(type.toString());
        this.type = type;
    }
    
    public Type getType(){
        return type;
    }
}
