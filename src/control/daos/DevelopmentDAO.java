package control.daos;

import control.dtos.DevelopmentDTO;;
import java.time.LocalDate;
import java.util.List;
import model.Development;

public class DevelopmentDAO {
    public DevelopmentDAO(){}
    
    public void addDevelopment(DevelopmentDTO dto){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Development getDevelopment(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Development> getDevelopments(long taskId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //note: here either start or end might be null
    public List<Development> getDevelopments(long taskId, LocalDate start, LocalDate end){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
