package parse.parsers;

import java.util.List;
import parse.IParser;

public class JSONParser<T> implements IParser {

    public JSONParser(){}
    
    @Override
    public T parse(String fliepath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<T> parseMany(String filepath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
