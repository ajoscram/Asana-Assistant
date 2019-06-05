package control.daos;

import control.ControlException;
import control.dtos.UserDTO;
import java.util.List;
import model.User;
import control.daos.connection.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    
    public UserDAO(){}
    
    public User login(String email, String password) throws ControlException{
        try{
            if(email.isEmpty()){
                email = null;
            }
            if(password.isEmpty()){
                password = null;
            }
            ResultSet rs = Connection.getInstance().query("EXEC USP_LOGIN '"+email+"','"+password+"'");
            if(rs.next()==true){
                Long IDcollaborator=rs.getLong("IDcollaborator");
                String username = rs.getString("name");
                String useremail = rs.getString("email");
                Long userasanaid=rs.getLong("asanaid");
                int registeredtemp = rs.getInt("registered");
                Boolean userregistered;
                if(registeredtemp == 0){
                    userregistered = false;
                }else{
                    userregistered = true;
                }
                return new User(IDcollaborator,userasanaid,username,useremail,userregistered);
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
            Connection.getInstance().query("EXEC USP_ADDUSER '"+name+"','"+email+"',"+id);
        } catch(SQLException ex){
            int errorCode = ex.getErrorCode();
            String errorMessage = ex.getMessage();
            switch(errorCode){
                case 70000:
                    throw new ControlException(ControlException.Type.EMPTY_SPACES,errorMessage);
                case 70001:
                    return;
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
            ResultSet rs;
            if(id==0){
                String ids = null;
                 rs = Connection.getInstance().query("EXEC USP_GETUSER "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETUSER "+id);
            }
            if(rs.next()==true){
                Long IDcollaborator=rs.getLong("IDcollaborator");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Long asanaid=rs.getLong("asanaid");
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
        try{
            ResultSet rs;
            if(taskId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETASIGNEE "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETASIGNEE "+taskId);
            }
            if(rs.next()==true){
                Long IDcollaborator=rs.getLong("IDcollaborator");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Long asanaid=rs.getLong("asanaid");
                int registeredtemp = rs.getInt("registered");
                Boolean registered;
                if(registeredtemp == 0){
                    registered = false;
                }else{
                    registered = true;
                }
                return new User(IDcollaborator,asanaid,name,email,registered);
            }else{
                throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,"Error: Task not found");
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
    
    public User getAdministrator(long projectId) throws ControlException{
        try{
            ResultSet rs;
            if(projectId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETADMINISTRATOR "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETADMINISTRATOR "+projectId);
            }
            if(rs.next()==true){
                Long IDcollaborator=rs.getLong("IDcollaborator");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Long asanaid=rs.getLong("asanaid");
                int registeredtemp = rs.getInt("registered");
                Boolean registered;
                if(registeredtemp == 0){
                    registered = false;
                }else{
                    registered = true;
                }
                return new User(IDcollaborator,asanaid,name,email,registered);
            }else{
                throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,"Error: Administrator Not Found");
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
    
    public List<User> getActiveUsers(long projectId) throws ControlException{
        try{
            ResultSet rs;
            if(projectId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETACTIVEUSERS "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETACTIVEUSERS "+projectId);
            }
            ArrayList<User> listaUsuarios = new ArrayList<User>();
            
            while(rs.next()){
                    if(rs.getInt("IDrol")==2){
                        Long IDcollaborator=rs.getLong("IDcollaborator");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        Long asanaid=rs.getLong("asanaid");
                        int registeredtemp = rs.getInt("registered");
                        Boolean registered;
                        if(registeredtemp == 0){
                            registered = false;
                        }else{
                            registered = true;
                        }
                        User user = new User(IDcollaborator,asanaid,name,email,registered);
                        listaUsuarios.add(user);
                    }
            }
            //System.out.print(listaUsuarios.get(0).getName());
            return listaUsuarios;
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
    
    public List<User> getBannedUsers(long projectId) throws ControlException{
        try{
            ResultSet rs;
            if(projectId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETBANNEDUSERS "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETBANNEDUSERS "+projectId);
            }
            ArrayList<User> listaUsuarios = new ArrayList<User>();
            
            while(rs.next()){
                    if(rs.getInt("IDrol")==2){
                        Long IDcollaborator=rs.getLong("IDcollaborator");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        Long asanaid=rs.getLong("asanaid");
                        int registeredtemp = rs.getInt("registered");
                        Boolean registered;
                        if(registeredtemp == 0){
                            registered = false;
                        }else{
                            registered = true;
                        }
                        User user = new User(IDcollaborator,asanaid,name,email,registered);
                        listaUsuarios.add(user);
                    }
            }
            //System.out.print(listaUsuarios.get(0).getName());
            return listaUsuarios;
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
}
