package control.daos;

import control.ControlException;
import control.daos.connection.Connection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Evidence;

public class EvidenceDAO {
    
    private static final String EVIDENCE_FOLDER_PATH = "evidence";
    
    public EvidenceDAO(){}
    
    public void addEvidence(long developmentId, String filepath) throws ControlException{
        try{
            File file = new File(filepath);
            if(file.isFile()){
                new File(EVIDENCE_FOLDER_PATH).mkdir();
                String filename = file.getName();
                Path newPath = Paths.get(EVIDENCE_FOLDER_PATH + File.separator + filename);
                Files.copy(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
                Connection.getInstance().queryinsert("EXEC USP_ADDEVIDENCE "+developmentId+","+"'"+filename+"'");
            } else
                throw new ControlException(ControlException.Type.IN_DEVELOPMENT, "EvidenceDAO.addEvidence needs IO_ERROR");
        } catch(IOException ex){
            throw new ControlException(ControlException.Type.IN_DEVELOPMENT, "EvidenceDAO.addEvidence needs IO_ERROR");
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
    
    public void downloadEvidence(long id, String path) throws ControlException{
        try{
            if(!new File(path).isDirectory())
                throw new ControlException(ControlException.Type.IN_DEVELOPMENT, "EvidenceDAO.downloadEvidence needs IO_ERROR");
            else{
                Evidence evidence = getEvidence(id);
                File file = new File(EVIDENCE_FOLDER_PATH + File.separator + evidence.getFilename());
                if(!file.isFile())
                    throw new ControlException(ControlException.Type.IN_DEVELOPMENT, "EvidenceDAO.downloadEvidence needs IO_ERROR");
                else{
                    Path newPath = Paths.get(path + File.separator + evidence.getFilename());
                    Files.copy(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch(IOException ex){
            throw new ControlException(ControlException.Type.IN_DEVELOPMENT, "EvidenceDAO.downloadEvidence needs IO_ERROR");
        }
    }
}
