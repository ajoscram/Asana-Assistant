package parse;

public class ParserException extends Exception {

    public enum Type {  }
    
    private Type type;
    
    public ParserException(Type type) {
        super(type.toString());
        this.type = type;
    }
    
    public Type getType(){
        return type;
    }
}
