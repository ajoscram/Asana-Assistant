package control.controllers;

import control.ControlException;
import control.daos.DevelopmentDAO;
import control.dtos.DevelopmentDTO;
import control.dtos.DevelopmentFilter;
import control.dtos.DisplayString;
import java.util.ArrayList;
import java.util.List;
import model.Development;

public class DevelopmentController {
    
    private DevelopmentDAO dao;
    
    public DevelopmentController(){
        dao = new DevelopmentDAO();
    }
    
    public void addDevelopment(long taskId,DevelopmentDTO dto) throws ControlException {
        dao.addDevelopment(taskId,dto);
    }
    
    public Development getDevelopment(long id) throws ControlException {
        return dao.getDevelopment(id);
    }
    
    public List<DisplayString> getDevelopmentStrings(long taskId) throws ControlException {
        return getDevelopmentStrings(taskId, DevelopmentFilter.EMPTY);
    }
    
    public List<DisplayString> getDevelopmentStrings(long id, DevelopmentFilter filter) throws ControlException {
        List<Development> developments;
        developments = dao.getDevelopments(id, filter.getStart(), filter.getEnd());
        List<DisplayString> strings = new ArrayList();
        String string;
        for(Development development : developments){
            string = "[" + development.getDate() + " " + development.getHours() + "h] " + development.getDescription();
            strings.add(new DisplayString(development.getId(), string));
        }
        return strings;
    }
}
