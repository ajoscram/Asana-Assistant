package control.controllers;

import control.ControlException;
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
    
    public void addEvidence(long developmentId, String filePath) throws ControlException {
        dao.addEvidence(developmentId, filePath);
    }
    
    public Evidence getEvidence(long id) throws ControlException {
        return dao.getEvidence(id);
    }
    
    public List<DisplayString> getEvidenceStrings(long id) throws ControlException {
        List<Evidence> evidence_ = dao.getEvidences(id);
        List<DisplayString> strings = new ArrayList();
        for(Evidence evidence : evidence_)
            strings.add(new DisplayString(evidence.getId(), evidence.getFilename()));
        return strings;
    }
    
    public void downloadEvidence(long id, String path) throws ControlException {
        dao.downloadEvidence(id, path);
    }
}
