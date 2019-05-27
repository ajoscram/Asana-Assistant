package report;

public class ReportException extends Exception {

    public enum Type { 
        SECTION_NAME_NOT_FOUND,
        FILE_IO_ERROR,
        INCORRECT_REPORT_FORMATTING,
        UNKNOWN_SECTION_TYPE
    }
    
    private Type type;
    
    public ReportException(Type type) {
        super(type.toString());
        this.type = type;
    }
    
    public Type getType(){
        return type;
    }
}
