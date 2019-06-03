package control;

public class ControlException extends Exception {

    public enum Type {
        IN_DEVELOPMENT,
        SUBTASK_NOT_TYPED_CORRECTLY,
        UNKNOWN_PARSER_TYPE,
        UNKNOWN_PRINTER_TYPE,
        COULD_NOT_CONNECT_TO_DATABASE,
        EMPTY_SPACES,/*70000*/
        UNKNOWN_ERROR,/*77777*/
        DUPLICATE_VALUE,/*70001,2601*/
        INVALID_LENGTH,/*103*/
        STORE_PROCEDURE_NOT_FOUND,/*2812*/
        INCOMPATIBLE_TYPE, /*8114*/
        NON_EXISTENT_VALUE, /*70002*/
        INHERIT_VALUE_ERROR, /*70004*/
        INVALID_EMAIL_FORMAT,
        FUNTIONALITY_NON_IMPLEMENTED,
    }
    
    private Type type;
    
    public ControlException(Type type, String message) {
        super(message);
        this.type = type;
    }
    
    public ControlException(Type type) {
        super(type.toString());
        this.type = type;
    }
    
    public Type getType(){
        return type;
    }
}
