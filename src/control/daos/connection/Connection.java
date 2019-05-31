package control.daos.connection;

import control.ControlException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import parse.ParseException;

public class Connection {
    
    private static final String DB_FILEPATH = "db.json";
    public static Connection instance;
    
    private java.sql.Connection connection;
    
    public static void connect() throws ControlException {
        try{
            instance = new ConnectionParser().parse(DB_FILEPATH);
        } catch(ParseException ex){
            throw new ControlException(ControlException.Type.COULD_NOT_CONNECT_TO_DATABASE);
        }
    }
    
    public void close() throws ControlException{
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new ControlException(ControlException.Type.COULD_NOT_CONNECT_TO_DATABASE);
        }
    }
    
    public static Connection getInstance(){
        return instance;
    }
    
    Connection(String url, String username, String password) throws SQLException{
        if(instance == null)
            connection = DriverManager.getConnection(url, username, password);
        else
            this.connection = instance.connection;
    }
    
    public ResultSet query(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
