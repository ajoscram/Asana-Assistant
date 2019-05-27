package report.sections;

import java.util.ArrayList;
import java.util.List;
import report.ReportException;
import report.Section;

public class ListSection extends Section{
    private ArrayList<Section> sections;
    
    public ListSection(String name){
        super(name);
        this.sections = new ArrayList();
    }
    
    public List<Section> getSections(){
        return sections;
    }
    
    public void addSection(Section section){
        sections.add(section);
    }
    
    public void removeSection(String name) throws ReportException {
        for(Section section : sections){
            if(section.getName().equals(name)) {
                sections.remove(section);
                return;
            }
        }
        throw new ReportException(ReportException.Type.SECTION_NAME_NOT_FOUND);
    }
}
