package control.daos.connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import parse.ParseException;

public class Connection {
    
    private static final String DB_FILEPATH = "db.json";
    private static Connection instance;
    
    public static void connect() throws ParseException {
        instance = new ConnectionParser().parse(DB_FILEPATH);
    }
    
    public static Connection getInstance(){
        return instance;
    }
    
    Connection(String url, String username, String password){
        //hacer la conexion real
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ResultSet query(String query) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
