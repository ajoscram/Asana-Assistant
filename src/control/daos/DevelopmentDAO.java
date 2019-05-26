package control.daos;

import control.dtos.DevelopmentDTO;
import control.dtos.Filter;
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
    
    public List<Development> getDevelopments(Filter filter){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
