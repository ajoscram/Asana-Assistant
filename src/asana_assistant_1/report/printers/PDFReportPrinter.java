package asana_assistant_1.report.printers;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import asana_assistant_1.report.IReportPrinter;
import asana_assistant_1.report.Report;
import asana_assistant_1.report.ReportException;
import asana_assistant_1.report.Section;
import asana_assistant_1.report.sections.ListSection;
import asana_assistant_1.report.sections.TextSection;

public class PDFReportPrinter implements IReportPrinter {
    
    public PDFReportPrinter(){}
    
    protected Chunk getChunk(TextSection section){
        return new Chunk(section.getText());
    }
    
    protected List getList(ListSection listSection) throws ReportException {
        List list = new List(List.UNORDERED, 10);
        for(Element element : parse(listSection.getSections())){
            if(element instanceof Paragraph)
                list.add(new ListItem((Paragraph)element));
            else if(element instanceof List)
                list.add((List)element);
        }
        return list;
    }
    
    protected java.util.List<Element> parse(java.util.List<Section> sections) throws ReportException {
        ArrayList<Element> elements = new ArrayList();
        for(Section section : sections){
            if(section instanceof ListSection){
                if(section.getName() != null)
                    elements.add(new Paragraph(section.getName() + ": "));
                elements.add(getList((ListSection)section));
            }
            else if(section instanceof TextSection){
                Paragraph text;
                if(section.getName() != null)
                    text = new Paragraph(section.getName() + ": ");
                else
                    text = new Paragraph();
                text.add(getChunk((TextSection) section));
                elements.add(text);
            }
            else
                throw new ReportException(ReportException.Type.UNKNOWN_SECTION_TYPE);
        }
        return elements;
    }
    
    @Override
    public void print(Report report, String filepath) throws ReportException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.open();
            for(Element element : parse(report.getSections()))
                document.add(element);
            document.close();
        } catch (FileNotFoundException ex) {
            throw new ReportException(ReportException.Type.FILE_IO_ERROR);
        } catch (DocumentException ex) {
            throw new ReportException(ReportException.Type.INCORRECT_REPORT_FORMATTING);
        }
    }
}