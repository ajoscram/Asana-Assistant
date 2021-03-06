package asana_assistant_1.control.daos;

import asana_assistant_1.control.ControlException;
import asana_assistant_1.control.daos.connection.Connection;
import asana_assistant_1.control.dtos.DevelopmentDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.model.Development;

public class DevelopmentDAO {
    public DevelopmentDAO(){}
    
    private String getDateString(LocalDate date){
        if(date != null)
            return "'" + date + "'";
        else
            return null;
    }
    private void addEvidence(DevelopmentDTO developmentDTO,Long iddevelopment) throws ControlException{
        int largo=developmentDTO.getEvidenceFilepaths().size()-1;
        for(int i=0;i<=largo;i++){
            new EvidenceDAO().addEvidence(iddevelopment,developmentDTO.getEvidenceFilepaths().get(i));
        }
        
    }
    public void addDevelopment(long idTask,DevelopmentDTO dto) throws ControlException{
        try{
            ResultSet rs;
            LocalDate date = dto.getDate();
            int hours = dto.getHours();
            String description = dto.getDescription();
            if(description.isEmpty()){
                description = null;
            }
            rs=Connection.getInstance().query("EXEC USP_ADDDEVELOPMENT "+hours+",'"+description+"',"+idTask + ", " + getDateString(date));
            if(rs.next()){
                Long iddevelopment = rs.getLong("IDdevelopment");
                if(!dto.getEvidenceFilepaths().isEmpty()){
                    int largo=dto.getEvidenceFilepaths().size()-1;
                    addEvidence(dto,iddevelopment);
                }
            }
            
            
            
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
                case 70002:
                    throw new ControlException(ControlException.Type.NON_EXISTENT_VALUE,errorMessage);
                default:
                    throw new ControlException(ControlException.Type.UNKNOWN_ERROR,errorMessage);
            }
        }
    }
    
    public Development getDevelopment(long id) throws ControlException{
        /*VERIFICAR PORQUE HAY DOS DATES*/
        try{
            ResultSet rs;
            if(id==0){
                String ids = null;
                 rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENT "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENT "+id);
            }
            if(rs.next()==true){
                Long IDdevelopment=rs.getLong("IDdevelopment");
                int hours = rs.getInt("hoursx");
                String description = rs.getString("descriptionx");
                String date = rs.getString("datecreated");
                LocalDate datecreated = LocalDate.parse(date);
                
                return new Development(IDdevelopment,datecreated,hours,description,datecreated);
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
    
    public List<Development> getDevelopments(long taskId) throws ControlException{
        try{
            ResultSet rs;
            if(taskId==0){
                String ids = null;
                rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENTSTASK "+ids);
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENTSTASK "+taskId);
            }
            ArrayList<Development> listaDevelopment= new ArrayList<>();
            
            while(rs.next()){
                Long IDdevelopment=rs.getLong("IDdevelopment");
                int hours = rs.getInt("hoursx");
                String description = rs.getString("descriptionx");
                String date = rs.getString("datecreated");
                LocalDate datecreated = LocalDate.parse(date);
                Development development = new Development(IDdevelopment,datecreated,hours,description,datecreated);
                listaDevelopment.add(development);     
            }
            return listaDevelopment;
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
    
    //note: here either start or end might be null
    public List<Development> getDevelopments(long taskId, LocalDate start, LocalDate end) throws ControlException{
        try{
            ResultSet rs;
            if(start==null && end ==null){
                rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENTSINITIALFINAL "+taskId+","+start+","+end+"");
            }else if(start!=null && end!=null){
                 rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENTSINITIALFINAL "+taskId+",'"+start+"','"+end+"'");
            }else if(start!=null && end==null){
                rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENTSWITHINITIALDATE "+taskId+",'"+start+"'");
            }else if(start==null && end!=null){
                rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENTSWITHFINALDATE "+taskId+",'"+end+"'");
            }else{
                rs = Connection.getInstance().query("EXEC USP_GETDEVELOPMENTSINITIALFINAL "+null+",'"+start+"','"+end+"'");
            }
            ArrayList<Development> listaDevelopment= new ArrayList<>();
            
            while(rs.next()){
                Long IDdevelopment=rs.getLong("IDdevelopment");
                int hours = rs.getInt("hoursx");
                String description = rs.getString("descriptionx");
                String date = rs.getString("datecreated");
                LocalDate datecreated = LocalDate.parse(date);
                Development development = new Development(IDdevelopment,datecreated,hours,description,datecreated);
                listaDevelopment.add(development);     
            }
            return listaDevelopment;
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
