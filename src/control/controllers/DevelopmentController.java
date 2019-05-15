package control.controllers;

import control.dtos.DevelopmentDTO;
import control.dtos.DisplayString;
import java.util.List;
import model.Development;

public class DevelopmentController {
    
    public DevelopmentController(){}
    
    public void addDevelopment(DevelopmentDTO dto){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Development getDevelopment(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void addEvidence(long developmentId, String filePath){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<DisplayString> getEvidenceStrings(long id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
