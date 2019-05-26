package parse.parsers;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import parse.IParser;
import parse.ParseException;

public class JSONParser<T> implements IParser {
    
    private Class<T> class_;
    
    public JSONParser(){
        this.class_ = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(this.class_);
    }
    
    private Object read(String filepath) throws ParseException {
        try{
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Object object = parser.parse(new FileReader(filepath));
            return object;
        } catch(IOException e) {
            throw new ParseException(ParseException.Type.IO);
        } catch (org.json.simple.parser.ParseException ex) {
            throw new ParseException(ParseException.Type.STRUCTURE);
        }
    }
    
    private T parse(JSONObject json) throws ParseException {
        return null;//Class class_ = T
    }

    private List<T> parse(JSONArray array) throws ParseException{
        ArrayList<T> list = new ArrayList();
        for(Object object : array){
            if(object == null)
                list.add(null);
            if(object instanceof JSONObject)
                list.add(parse((JSONObject)object));
            else if(object instanceof JSONArray)
                list.addAll(parse((JSONArray) object));
        }
        return list;
    }
    
    //IParser
    @Override
    public T parse(String filepath) throws ParseException {
        Object read = read(filepath);
        if(read == null)
            return null;
        if(read instanceof JSONObject)
            return parse((JSONObject)read);
        else
            throw new ParseException(ParseException.Type.STRUCTURE);
    }
    
    @Override
    public List<T> parseMany(String filepath) throws ParseException {
        Object read = read(filepath);
        if(read == null)
            return null;
        if(read instanceof JSONArray)
            return parse((JSONArray)read);
        else
            throw new ParseException(ParseException.Type.STRUCTURE);
    }   
}