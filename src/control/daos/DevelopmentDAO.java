package control.daos;

import control.dtos.DevelopmentDTO;
import java.util.List;
import model.Development;
import model.Evidence;

public class DevelopmentDAO {
    public DevelopmentDAO(){}
    
    public void addDevelopment(DevelopmentDTO dto){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Development getDevelopment(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void addEvidence(long developmentId, String filepath){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Evidence> getEvidence(long developmentId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
