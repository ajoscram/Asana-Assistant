package control;

public class ControlException extends Exception {

    public enum Type {
        IN_DEVELOPMENT,
        SUBTASK_NOT_TYPED_CORRECTLY,
        UNKNOWN_PARSER_TYPE,
        UNKNOWN_PRINTER_TYPE,
        COULD_NOT_CONNECT_TO_DATABASE
    }
    
    private Type type;
    
    public ControlException(Type type) {
        super(type.toString());
        this.type = type;
    }
    
    public Type getType(){
        return type;
    }
}
