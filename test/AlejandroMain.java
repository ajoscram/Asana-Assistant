
import control.dtos.TaskDTO;
import java.io.File;
import parse.parsers.JSONParser;
import report.Report;
import report.ReportException;
import report.printers.PDFReportPrinter;
import report.sections.ListSection;
import report.sections.TextSection;

public class AlejandroMain {

    public static void main(String[] args) throws ReportException {
        /*TEST REPORTES*/
        Report report = new Report();
        TextSection title = new TextSection("title", "Lorem Ipsum");
        TextSection paragraph = new TextSection("paragraph", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        ListSection list = new ListSection("list");
        ListSection sublist = new ListSection("sublist");
        for(int i = 0; i <= 5; i++)
            list.addSection(new TextSection("item" + i, "Item #" + i));
        for(int i = 0; i <= 5; i++)
            sublist.addSection(new TextSection("item 5." + i, "Item #5." + i));
        list.addSection(sublist);
        for(int i = 6; i <= 10; i++)
            list.addSection(new TextSection("item" + i, "Item #" + i));
        
        report.addSection(title);
        report.addSection(paragraph);
        report.addSection(list);
        
        new PDFReportPrinter().print(report, "C:\\Users\\Alejandro\\Desktop\\report.pdf");
    }
}
