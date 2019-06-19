package asana_assistant_1.report.sections;

import asana_assistant_1.report.Section;

public class TextSection extends Section {
    private String text;
    
    public TextSection(String name, String text){
        super(name);
        this.text = text;
    }
    
    public String getText(){
        return text;
    }
    
    public void setText(String text){
        this.text = text;
    }
}
