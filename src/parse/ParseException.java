package parse;

public class ParseException extends Exception {

    public enum Type {
        IO, //file can't be read from or found
        STRUCTURE, //file has incorrect structure
        
    }
    
    private Type type;
    
    public ParseException(Type type) {
        super(type.toString());
        this.type = type;
    }
    
    public Type getType(){
        return type;
    }
}
