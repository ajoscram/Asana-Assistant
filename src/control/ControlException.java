package control;

public class ControlException extends Exception {

    public enum Type { 
        SUBTASK_NOT_TYPED_CORRECTLY
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
