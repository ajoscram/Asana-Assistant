package control.controllers;

import control.daos.DevelopmentDAO;
import control.dtos.DevelopmentDTO;
import control.dtos.DisplayString;
import java.util.ArrayList;
import java.util.List;
import model.Development;
import model.Evidence;

public class DevelopmentController {
    
    private DevelopmentDAO dao;
    
    public DevelopmentController(){
        dao = new DevelopmentDAO();
    }
    
    public void addDevelopment(DevelopmentDTO dto){
        dao.addDevelopment(dto);
    }
    
    public Development getDevelopment(long id){
        return dao.getDevelopment(id);
    }
    
    public void addEvidence(long developmentId, String filePath){
        dao.addEvidence(developmentId, filePath);
    }
    
    public List<DisplayString> getEvidenceStrings(long id){
        List<Evidence> evidence_ = dao.getEvidence(id);
        List<DisplayString> strings = new ArrayList();
        for(Evidence evidence : evidence_)
            strings.add(new DisplayString(evidence.getId(), evidence.getFilename()));
        return strings;
    }
}
