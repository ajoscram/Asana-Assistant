package control.daos;

import control.ControlException;
import control.daos.connection.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Evidence;
import model.User;

public class EvidenceDAO {
    
    public EvidenceDAO(){}
    
    public void addEvidence(long developmentId, String filepath) throws ControlException{
        try{
            ResultSet rs;
            if(filepath.isEmpty()){
                filepath = null;
            }
            if(developmentId==0){
                String dId= null;
                rs = Connection.getInstance().query("EXEC USP_ADDEVIDENCE "+dId+","+filepath);
            }else{
                rs = Connection.getInstance().query("EXEC USP_ADDEVIDENCE "+developmentId+","+"'"+filepath+"'");
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
    
    public Evidence getEvidence(long id) throws ControlException{
        try{
            ResultSet rs;
            if(id==0){
                String ids = null;
                 rs = Connection.getInstance().query("EXEC USP_GETEVIDENCE "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETEVIDENCE "+id);
            }
            if(rs.next()==true){
                Long IDevidence=rs.getLong("IDevidence");
                String filepath = rs.getString("filepath");
               
                return new Evidence(IDevidence,filepath);
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
    
    public List<Evidence> getEvidences(long developmentId) throws ControlException{
        try{
            ResultSet rs;
            if(developmentId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETEVIDENCES "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETEVIDENCES "+developmentId);
            }
            ArrayList<Evidence> listaEvidencias = new ArrayList<Evidence>();
            
            while(rs.next()){
                Long IDevidence=rs.getLong("IDevidence");
                String filepath = rs.getString("filepath");
                Evidence evidence = new Evidence(IDevidence,filepath);
                listaEvidencias.add(evidence);
            }
            System.out.print(listaEvidencias.get(0).getFilename());
            return listaEvidencias;
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
    /*Que hace este downloadevidence*/
    public void downloadEvidence(long id, String path){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
