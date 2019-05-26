package control.controllers;

import control.daos.EvidenceDAO;
import control.dtos.DisplayString;
import java.util.ArrayList;
import java.util.List;
import model.Evidence;

public class EvidenceController {
    
    private EvidenceDAO dao;
    
    public EvidenceController(){
        dao = new EvidenceDAO();
    }
    
    public void addEvidence(long developmentId, String filePath){
        dao.addEvidence(developmentId, filePath);
    }
    
    public Evidence getEvidence(long id){
        return dao.getEvidence(id);
    }
    
    public List<DisplayString> getEvidenceStrings(long id){
        List<Evidence> evidence_ = dao.getEvidences(id);
        List<DisplayString> strings = new ArrayList();
        for(Evidence evidence : evidence_)
            strings.add(new DisplayString(evidence.getId(), evidence.getFilename()));
        return strings;
    }
    
    public void downloadEvidence(long id, String path){
        dao.downloadEvidence(id, path);
    }
}
