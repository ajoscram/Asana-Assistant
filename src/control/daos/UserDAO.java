package control.daos;

import control.ControlException;
import control.dtos.UserDTO;
import java.util.List;
import model.User;
import control.daos.connection.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    public UserDAO(){}
    
    public User login(String email, String password){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void registerUser(UserDTO user) throws ControlException{
        try{
            String name = user.getName();
            String password = user.getPassword();
            String email = user.getEmail();
            Long id = user.getAsanaId();
            Connection.getInstance().query("EXEC USP_REGISTERUSER '"+name+"','"+email+"',"+id+",'"+password+"'");
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70001:
                    throw new ControlException(ControlException.Type.DUPLICATE_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                case 70005:
                    throw new ControlException(ControlException.Type.INVALID_EMAIL_FORMAT,errorMessage);
                default:
                    break;
            }
        }
    }
    
    public void addUser(UserDTO user) throws ControlException{
        try{
            String name = user.getName();
            String email = user.getEmail();
            Long id = user.getAsanaId();
            if(name.isEmpty()){
                name = null;
            }
            if(email.isEmpty()){
                email = null;
            }
            if(id.toString().equals("")){
                Long asanaid=Long.valueOf(0);
                id = asanaid;
            }
            Connection.getInstance().query("EXEC USP_ADDUSER '"+name+"','"+email+"',"+id);
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70001:
                    throw new ControlException(ControlException.Type.DUPLICATE_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                case 70005:
                    throw new ControlException(ControlException.Type.INVALID_EMAIL_FORMAT,errorMessage);
                case 70006:
                    throw new ControlException(ControlException.Type.FUNTIONALITY_NON_IMPLEMENTED,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public User getUser(long id) throws ControlException{
        try{
            ResultSet rs = Connection.getInstance().query("EXEC USP_GETUSER "+id);
            if(rs.next()==true){
                int IDcollaboratortemp = rs.getInt("IDcollaborator");
                Long IDcollaborator=Long.valueOf(IDcollaboratortemp);
                
                String name = rs.getString("name");
                
                String email = rs.getString("email");
                
                int asanaidtemp = rs.getInt("asanaid");
                Long asanaid=Long.valueOf(asanaidtemp);
                
                int registeredtemp = rs.getInt("registered");
                Boolean registered;
                if(registeredtemp == 0){
                    registered = false;
                }else{
                    registered = true;
                }
                return new User(IDcollaborator,asanaid,name,email,registered);
            }else{
                throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,"Error: User not found");
            }
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                case 103:
                    throw new ControlException(ControlException.Type.INVALID_LENGTH,errorMessage);
                case 8114:
                    throw new ControlException(ControlException.Type.INCOMPATIBLE_TYPE,errorMessage);
                case 77777:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public User getAsignee(long taskId) throws ControlException{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public User getAdministrator(long projectId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<User> getActiveUsers(long projectId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<User> getBannedUsers(long projectId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
