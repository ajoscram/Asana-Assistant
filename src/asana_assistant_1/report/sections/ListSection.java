package asana_assistant_1.report.sections;

import java.util.ArrayList;
import java.util.List;
import asana_assistant_1.report.ReportException;
import asana_assistant_1.report.Section;

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
    
    public void addSections(List<Section> sections){
        this.sections.addAll(sections);
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
