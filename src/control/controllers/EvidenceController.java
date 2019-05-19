package control.controllers;

import control.daos.EvidenceDAO;
import model.Evidence;

public class EvidenceController {
    
    private EvidenceDAO dao;
    
    public EvidenceController(){
        dao = new EvidenceDAO();
    }
    
    public Evidence getEvidence(long id){
        return dao.getEvidence(id);
    }
    
    public void downloadEvidence(long id, String path){
        dao.downloadEvidence(id, path);
    }
}
