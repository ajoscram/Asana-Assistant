package report;

import java.util.ArrayList;
import java.util.List;

public class Report {
    
    private ArrayList<Section> sections;
    
    public Report(){
        this.sections = new ArrayList();
    }
    
    public List<Section> getSections(){
        return sections;
    }
    
    public Section getSection(String name) throws ReportException{
        for(Section section : sections)
            if(section.getName().equals(name))
                return section;
        throw new ReportException(ReportException.Type.SECTION_NAME_NOT_FOUND);
    }
    
    public void addSections(List<Section> sections){
        this.sections.addAll(sections);
    }
    
    public void addSection(Section section){
        sections.add(section);
    }
    
    public void removeSection(String name) throws ReportException {
        Section section = getSection(name);
        sections.remove(section);
    }
}