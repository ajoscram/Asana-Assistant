package control.daos.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import org.json.simple.JSONObject;
import parse.IParser;
import parse.ParseException;

class ConnectionParser implements IParser<Connection> {
    
    private static final String URL_KEY = "url";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    
    private Object read(String filepath) throws ParseException {
        try{
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            Object object = parser.parse(new FileReader(filepath));
            return object;
        } catch(IOException e) {
            throw new ParseException(ParseException.Type.FILE_IO_ERROR);
        } catch (org.json.simple.parser.ParseException ex) {
            throw new ParseException(ParseException.Type.STRUCTURE);
        }
    }
    
    @Override
    public Connection parse(String filepath) throws ParseException {
        try {
            JSONObject read = (JSONObject)read(filepath);
            String url = (String)read.get(URL_KEY);
            String username = (String)read.get(USERNAME_KEY);
            String password = (String)read.get(PASSWORD_KEY);
            if(url == null || username == null || password == null)
                throw new ParseException(ParseException.Type.STRUCTURE);
            else
                return new Connection(url, username, password);
        } catch(NullPointerException | ClassCastException ex) {
            ex.printStackTrace();
            throw new ParseException(ParseException.Type.STRUCTURE);
        } catch(SQLException ex) {
            throw new ParseException(ParseException.Type.FILE_IO_ERROR);
        }
    }
    
}
