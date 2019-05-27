package control.controllers;

import control.daos.DevelopmentDAO;
import control.dtos.DevelopmentDTO;
import control.dtos.DisplayString;
import control.dtos.Filter;
import java.util.ArrayList;
import java.util.List;
import model.Development;

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
    
    public List<DisplayString> getDevelopmentStrings(long id){
        return getDevelopmentStrings(id, null);
    }
    
    public List<DisplayString> getDevelopmentStrings(long id, Filter filter){
        List<Development> developments;
        if(filter == null)
            developments = dao.getDevelopments(id);
        else
            developments = dao.getDevelopments(id, filter.getStart(), filter.getEnd());
        List<DisplayString> strings = new ArrayList();
        String string;
        for(Development development : developments){
            string = "[" + development.getDate() + "]" + development.getDescription();
            strings.add(new DisplayString(development.getId(), string));
        }
        return strings;
    }
}
